package matrix;

import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
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
        if(vector == null) {
        	return null;
        }
        
    	NavigableVector<S> vectorToReturn = new NavigableVector<S>(new TreeMap<Integer, S>(), zero);
    	
    	for(Entry<Integer, S> e: vector.entrySet()) {
    		if(e!= null && !e.getValue().equals(zero)) {
    			vectorToReturn.matrix.put(e.getKey(), e.getValue());
    		}
    	}
    	return vectorToReturn;
    }
  
}
