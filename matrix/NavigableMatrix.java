package matrix;


import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Stream;


public final class NavigableMatrix<T> implements Matrix<Indexes, T>{

	private final NavigableMap<Indexes, T> matrixByRows;
	private final T zero;
	private final NavigableMap<Indexes, T> matrixByColumns;
	
	private NavigableMatrix(NavigableMap<Indexes, T> mapByRows, T z, NavigableMap<Indexes, T> mapByCols) {
		matrixByRows = Collections.unmodifiableNavigableMap(mapByRows);
		zero = z;
		matrixByColumns = Collections.unmodifiableNavigableMap(mapByCols);
	}
	
	//Helper method that finds the transpose of a map
	private static <S> NavigableMap<Indexes, S> transpose(NavigableMap<Indexes, S> matrix, S zero){
		NavigableMap<Indexes, S> transposeMap = new TreeMap<>();
		for(Entry<Indexes, S> e: matrix.entrySet()) {
			transposeMap.put(new Indexes(e.getKey().column(), e.getKey().row()), e.getValue());
		}
		return transposeMap;
	}
	
	//Creates instance of NavigableMatrix according to valueMapper function (if rows, columns are nonnegative and not zero)
	public static <S> NavigableMatrix<S> instance(int rows, int columns, Function<Indexes, S> valueMapper, S zero){
		int nonEmptyRows = InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, rows);
		int nonEmptyColumns = InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.COLUMN, columns);
		
		NavigableMap<Indexes, S> rowTree = treeHelper(nonEmptyRows, nonEmptyColumns, valueMapper, zero);
		return new NavigableMatrix<S>(rowTree, zero, NavigableMatrix.transpose(rowTree, zero));
	}
	//Helper method for ::instance and ::from to avoid repeated code, puts values into a tree based on a given mapper function
	private static <S> NavigableMap<Indexes, S> treeHelper (int rows, int columns, Function<Indexes, S> mapper, S zero){
		NavigableMap<Indexes, S> tree = new TreeMap<>();
		Stream<Indexes> rowStream = Indexes.stream(rows, columns).filter(n -> n != zero);
		rowStream.forEach(n -> tree.put(n, Objects.requireNonNull(mapper).apply(n)));
		
		return tree;
	}
	
	//Creates a NavigableMatrix with all entries equal to value
	public static <S> NavigableMatrix<S> constant(int rows, int columns, S value, S zero) {
		return instance(rows, columns, n -> value, zero);
	}
	
	//Returns an identity matrix with entries along the diagonal matrix equal to identity
	public static <S> NavigableMatrix<S> identity(int size, S zero, S identity){
		return instance(size, size, n -> n.areDiagonal() ? identity : zero, zero);
	}
	
	//Returns a copy NavigableMatrix from a NavigableMap
	public static <S> NavigableMatrix<S> from(NavigableMap<Indexes, S> matrix, S zero){
		Objects.requireNonNull(matrix);
		Objects.requireNonNull(zero);
		
		NavigableMap<Indexes, S> columnMap = new TreeMap<>();
		for(Entry<Indexes, S> e: matrix.entrySet()) {
			columnMap.put(new Indexes(e.getKey().column(), e.getKey().row()), e.getValue());
		}
		return new NavigableMatrix<S>(matrix, zero, NavigableMatrix.transpose(columnMap, zero));
	}
	
	//Returns a copy NavigableMatrix from a 2D array
	public static <S> NavigableMatrix<S> from(S[][] matrix, S zero){
		Objects.requireNonNull(matrix);
		Objects.requireNonNull(zero);
		
		NavigableMap<Indexes, S> rowTree = treeHelper(matrix.length, matrix[0].length, n -> n.value(matrix), zero);
		
		return new NavigableMatrix<S>(rowTree, zero, NavigableMatrix.transpose(rowTree, zero));
	}
	public NavigableVector<T> row (int i){
		return matrixToVector(i, true);
	}
	public NavigableVector<T> column (int j){
		return matrixToVector(j, false);
	}
	//Helper method for row and column to avoid repeated code
	private NavigableVector<T> matrixToVector (int k, boolean isRow){
		assert k>=0;
		
		NavigableMap<Integer, T> vector = new TreeMap<>();
		NavigableMap<Indexes, T> matrixFormat = isRow ? matrixByRows: matrixByColumns;
		
		for(Entry<Indexes, T> e: matrixFormat.entrySet()) {
			if(e.getKey().row() == k) {
				vector.put(e.getKey().column(), e.getValue());
			}
		}
		return NavigableVector.from(vector, zero);
	}
	
	public T value(Indexes indexes) {
		return matrixByRows.getOrDefault(Objects.requireNonNull(indexes), zero);
	}
	public T zero() {
		return zero;
	}
	public NavigableMap<Indexes, T> representation() {
		return new TreeMap<Indexes, T>(matrixByRows);
	}
	public PeekingIterator<Map.Entry<Indexes, T>> peekingIterator(){
    	return PeekingIterator.from(matrixByRows.entrySet().iterator());
    }
	
	public Matrix<Indexes, T> merge(Matrix<Indexes, T> other, BinaryOperator<T> op) {
		InconsistentZeroException.requireMatching(this, other);
		return from(MapMerger.merge(
				peekingIterator(), other.peekingIterator(), Comparator.naturalOrder(), 
				op, Indexes.ORIGIN, zero), zero);
	}
	
	//Exception class for index OOB error
	public static class InvalidLengthException extends Exception {
		private static final long serialVersionUID = -7511319868778756369L;
		private Cause cause;
		private int length;
		
		public InvalidLengthException(Cause c, int len) {
			cause = c;
			length = len;
		}
		
		public static enum Cause {
				ROW, COLUMN
		}
		
		public Cause getErrorCause() {
			return cause;
		}
		public int getLength() {
			return length;
		}
		public static int requireNonEmpty(Cause cause, int length) throws IllegalArgumentException {
			if(length <= 0) {
				throw new IllegalArgumentException(new InvalidLengthException(cause, length));
			}
			return length;
		}
	}

}
