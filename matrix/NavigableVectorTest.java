/**
 * 
 */
package matrix;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BinaryOperator;

import org.junit.Test;
import static org.junit.Assert.assertNull;

/**
 * @author Lucas Smith
 * @since 02-01-24
 * @version 1.0 
 * 
 */
public class NavigableVectorTest {

	/**
	 * Test method for {@link matrix.NavigableVector#from(java.util.Map, java.lang.Object)}.
	 */
	@Test
	public void testFrom() {
		Map <Integer, Integer> tester = new HashMap<>();
		tester.put(32, 31);
		tester.put(0, 0);
		tester.put(12, 756);
		tester.put(0, 0);
		NavigableVector.from(tester, 0);
		for(Entry<Integer, Integer> e: tester.entrySet()) {
			assertNotEquals("Copy of map not equivalent to original", e, 0);
		}
		tester.put(null, null);
		assertNull(NavigableVector.from(null, 0));
	}
	@Test
	public void testMerge() {
		Map <Integer, Integer> tester1 = new HashMap<>();
		tester1.put(2, 1);	
		tester1.put(4, 5);

		NavigableVector.from(tester1, 0);
		
		Map <Integer, Integer> tester2 = new HashMap<>();
		tester2.put(3, 3);
		tester2.put(2, 6);

		NavigableVector.from(tester2, 0);
		BinaryOperator<Integer> add = (a, b) -> a + b;
		assertEquals(tester1.merge(tester2, add, 0) == tester1.equals(tester2, true));
	}
	

}
