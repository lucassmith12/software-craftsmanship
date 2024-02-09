package matrix;

import java.util.Collections;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Stream;


public final class NavigableMatrix<T> implements Matrix<Indexes, T>{

	private final NavigableMap<Indexes, T> matrix;
	private final T zero;
	
	private NavigableMatrix(NavigableMap<Indexes, T> m, T z) {
		matrix = m;
		zero = z;
	}
	
	
	//Creates instance of NavigableMatrix according to valueMapper function (if rows, columns are nonnegative and not zero)
	public static <S> NavigableMatrix<S> instance(int rows, int columns, Function<Indexes, S> valueMapper, S zero){
		TreeMap<Indexes, S> tree = new TreeMap<Indexes, S>();
		int nonEmptyRows = InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, rows);
		int nonEmptyColumns = InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.COLUMN, columns);
		
		Stream<Indexes> indexStream = Indexes.stream(nonEmptyRows, nonEmptyColumns);
		indexStream.forEach(n -> tree.put(n, valueMapper.apply(n)));
		return new NavigableMatrix<S>(Collections.unmodifiableNavigableMap(tree), zero);
	}
	
	//Creates a NavigableMatrix with all entries equal to value
	public static <S> NavigableMatrix<S> constant(int rows, int columns, S value, S zero) {
		TreeMap<Indexes, S> tree = new TreeMap<Indexes, S>();
		int nonEmptyRows = InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, rows);
		int nonEmptyColumns = InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.COLUMN, columns);
		
		Stream<Indexes> indexStream = Indexes.stream(nonEmptyRows, nonEmptyColumns);
		indexStream.forEach(n -> tree.put(n, value));
		return new NavigableMatrix<S>(Collections.unmodifiableNavigableMap(tree), zero);
	}
	
	//Returns an identity matrix with entries along the diagonal matrix equal to identity
	public static <S> NavigableMatrix<S> identity(int size, S zero, S identity){
		TreeMap<Indexes, S> tree = new TreeMap<Indexes, S>();
		int nonEmptySize = InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, size);
		
		Stream<Indexes> indexStream = Indexes.stream(new Indexes(nonEmptySize, nonEmptySize));
		indexStream.forEach(n -> tree.put(n, n.areDiagonal() ? identity : zero));
		return new NavigableMatrix<S>(Collections.unmodifiableNavigableMap(tree), zero);
	}
	
	//Returns a copy NavigableMatrix from a NavigableMap
	public static <S> NavigableMatrix<S> from(NavigableMap<Indexes, S> matrix, S zero){
		return new NavigableMatrix<S>(Collections.unmodifiableNavigableMap(matrix), zero);
	}
	
	//Returns a copy NavigableMatrix from a 2D array
	public static <S> NavigableMatrix<S> from(S[][] matrix, S zero){
		TreeMap<Indexes, S> tree = new TreeMap<Indexes, S>();
		
		Stream<Indexes> indexStream = Indexes.stream(new Indexes(matrix.length, matrix[0].length));
		indexStream.forEach(n -> tree.put(n, n.value(matrix)));
		return new NavigableMatrix<S>(Collections.unmodifiableNavigableMap(tree), zero);
	}
	
	public T value(Indexes indexes) {
		if(matrix.get(indexes).equals(null)) {
			return zero;
		}
		return matrix.get(indexes);
	}
	public T zero() {
		return zero;
	}
	public NavigableMap<Indexes, T> representation() {
		return new TreeMap<Indexes, T>(matrix);
	}
	public PeekingIterator<Map.Entry<Indexes, T>> peekingIterator(){
    	return PeekingIterator.from(matrix.entrySet().iterator());
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
