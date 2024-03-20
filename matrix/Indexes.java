package matrix;

import java.util.*;
import java.util.stream.Stream;
/**
 * Indexes record -- represents the indices of an entry in a matrix 
 * @author Lucas Smith
 * @since 02-01-24
 * @version 1.0
 * 
 */
public record Indexes(int row, int column)implements Comparable<Indexes> {
	public static final Indexes ORIGIN = new Indexes(0,0);
	
	//Returns Indexes of the same row/column as the input Indexes
	public Indexes withRow(int row) {
		return new Indexes(row, this.column);
	}
	public Indexes withColumn(int column) {
		return new Indexes(this.row,column);
	}
	
	//Returns a Comparator comparing indexes by rows, then by columns if rows are equal
	public static final Comparator<Indexes> byRows = (a,b) -> 
		a.row() != b.row() ? a.row() - b.row(): a.column() - b.column;
	//Returns a Comparator comparing indexes by columns, then by rows if columns are equal
	public static final Comparator<Indexes> byColumns = (a,b) -> 
		a.column() != b.column() ? a.column() - b.column(): a.row() - b.row;
	
	public <S> S value(S[][] matrix) {
		return matrix[row][column];
	}
	public <S> S value(Matrix<Indexes, S> matrix) {
		return matrix.value(this);
	}
	public boolean areDiagonal() {
		return row == column;
	}
	public int compareTo(Indexes index) {
		return byRows.compare(this, index);
	}
	
	//As long as no indexes are null, return a stream of all indexes between from and to
	public static Stream<Indexes> stream(Indexes from, Indexes to){ 
		from = Objects.requireNonNull(from);
		to = Objects.requireNonNull(to);
		
		List<Indexes> builder = new ArrayList<>();
		
		for(int i = from.row(); i<to.row(); i++) {
			for(int j = from.column(); j<to.column(); j++) {
				builder.add(new Indexes(i,j));
			}
		}
		Stream<Indexes> stream = builder.stream();
		return stream;
	}
	
	//Overloaded stream methods for convenience
	public static Stream<Indexes> stream(Indexes size) {
		return stream(ORIGIN, size);
	}
	public static Stream<Indexes> stream(int rows, int columns) {
		return stream(ORIGIN, new Indexes(rows, columns));
	}
}
