package matrix;


import java.util.NavigableMap;
import java.util.TreeMap;
/**
 * Abstract Matrix representation as an implementation of a matrix abstraction
 * @author Lucas Smith
 * @since 02-01-24
 * @version 1.0
 * 
 */
public abstract class AbstractMatrix<I,T> implements Matrix<I,T>{
    protected final NavigableMap<I, T> matrix;
    private final T zero;

    protected AbstractMatrix(NavigableMap<I,T> m, T z){
        matrix = m;
        zero = z;
    }
    
    public T value (I index) {
    	return matrix.get(index);
    }
    public T zero() {
    	return zero;
    }
    //Returns a copy of the balanced BST
    public NavigableMap<I, T> representation(){
    	NavigableMap<I,T> mapToReturn = new TreeMap<>();
    	mapToReturn.putAll(matrix);
    	return mapToReturn;
    }

}
