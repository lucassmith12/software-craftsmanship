/**
 * 
 */
package matrix;

import static org.junit.Assert.*;

import java.util.NavigableMap;
import java.util.TreeMap;

import org.junit.Test;

/**
 * @author Lucas Smith
 * @since 02-01-24
 * @version 1.0
 * 
 */
public class AbstractMatrixTest {
	/**
	 * Test method for {@link matrix.AbstractMatrix#representation()}.
	 */
	@Test
	public void testRepresentation() {
		NavigableMap<Integer, Integer> testMap = new TreeMap<>(); 

		testMap.put(4, 100); 
		testMap.put(3, 200); 
		testMap.put(2, 300); 
    	testMap.put(1, 400); 
    	
    	AbstractMatrix<Integer,Integer> actual = NavigableVector.from(testMap, 0);
    	
        assertEquals("The NavigableMaps should be equal", actual.representation(), actual.matrix);
	}

}
