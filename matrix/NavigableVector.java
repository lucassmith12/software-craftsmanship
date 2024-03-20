package matrix;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BinaryOperator;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.TreeMap;
/**
 * Navigable Vector -- represents a vector in a matrix
 * @author Lucas Smith
 * @since 02-01-24
 * @version 1.0
 * 
 */
public final class NavigableVector<T> extends AbstractMatrix<Integer,T>{
    
    private NavigableVector (NavigableMap<Integer, T> m, T z){
    	super(m,z);
    }
    public static <S> NavigableVector<S> from (Map<Integer, S> vector, S zero){
        vector = Objects.requireNonNull(vector);
    	TreeMap<Integer, S> entries = new TreeMap<Integer,S>(); 
    	//Do with streams
    	for(Entry<Integer, S> e: vector.entrySet()) {
    		if(e!= null && !e.getValue().equals(zero)) {
    			entries.put(e.getKey(), e.getValue());
    		}
    	}
    	NavigableVector<S> vectorToReturn = new NavigableVector<S>(entries, zero);
    	return vectorToReturn;
    }
	
    public PeekingIterator<Map.Entry<Integer, T>> peekingIterator(){
    	return PeekingIterator.from(matrix.entrySet().iterator());
    }
    
    
	public Matrix<Integer, T> merge(Matrix<Integer, T> other, BinaryOperator<T> op) {
		InconsistentZeroException.requireMatching(this, other);
		return NavigableVector.from(
				MapMerger.merge(peekingIterator(), other.peekingIterator(), 
				Comparator.naturalOrder(), op, 0, zero()), zero());
	}
  
}
