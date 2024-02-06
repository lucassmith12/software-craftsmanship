package matrix;

import java.util.NavigableMap;
/**
 * Matrix Interface
 * @author Lucas Smith
 * @since 02-01-24
 * @version 1.0
 * 
 */
interface Matrix<I,T> {
    public T value(I index);
    public T zero();
    public NavigableMap<I, T> representation();
}