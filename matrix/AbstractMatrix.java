package matrix;


import java.util.Collections;
import java.util.Comparator;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
/**
 * Abstract Matrix representation 
 * @author Lucas Smith
 * @since 02-01-24
 * @version 1.0
 * 
 */
public abstract class AbstractMatrix<I,T> implements Matrix<I,T>{
    protected final NavigableMap<I, T> matrix;
    private final T zero;

    protected AbstractMatrix(NavigableMap<I,T> m, T z){
        matrix = Collections.unmodifiableNavigableMap(m);
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
    	return new TreeMap<>(matrix);
    }
    
}
