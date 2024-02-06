/**
 * 
 */
package matrix;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

/**
 * @author Lucas Smith
 * @since 02-01-24
 * @version 1.0
 *
 */
public class IndexesTest {
	/**
	 * Test method for {@link matrix.Indexes#areDiagonal()}.
	 */
	@Test
	public void testAreDiagonal() {
		assertEquals("(0,0) should return true", new Indexes(0,0).areDiagonal(), true);
		assertEquals("(0,4) should return false", new Indexes(0,4).areDiagonal(), false);
		assertEquals("(2,2) should return true", new Indexes(2,2).areDiagonal(), true);		
		assertEquals("(8,4) should return false", new Indexes(8,4).areDiagonal(), false);
		assertEquals("(6,6) should return true", new Indexes(6,6).areDiagonal(), true);
	}

	/**
	 * Test method for {@link matrix.Indexes#stream(matrix.Indexes, matrix.Indexes)}.
	 */
	@Test
	public void testStreamIndexesIndexes() {
		Indexes from = new Indexes(1, 1);
        Indexes to = new Indexes(3, 3);

        List<Indexes> result = Indexes.stream(from, to).collect(Collectors.toList());

        assertEquals("Result size should match", 4, result.size());
        assertEquals("First element should be (1, 1)", new Indexes(1, 1), result.get(0));
        assertEquals("Second element should be (1, 2)", new Indexes(1, 2), result.get(1));
        assertEquals("Third element should be (2, 1)", new Indexes(2, 1), result.get(2));
        assertEquals("Fourth element should be (2, 2)", new Indexes(2, 2), result.get(3));
        
        final Indexes nullFrom = null;
        final Indexes nullTo = null;
        assertThrows(NullPointerException.class,() -> Indexes.stream(nullFrom, nullTo) );
        assertThrows(NullPointerException.class,() -> Indexes.stream(from, nullTo) );
        assertThrows(NullPointerException.class,() -> Indexes.stream(nullFrom, to) );
	}

	/**
	 * Test method for {@link matrix.Indexes#stream(matrix.Indexes)}.
	 */
	@Test
	public void testStreamIndexes() {
        Indexes to = new Indexes(3, 3);
      
        List<Indexes> oneIndexesResult = Indexes.stream(to).collect(Collectors.toList());
        List<Indexes> twoIndexesResult = Indexes.stream(new Indexes(0,0), to).collect(Collectors.toList());
        
		assertEquals("Results from streams should be equal", oneIndexesResult, twoIndexesResult);
	}

	/**
	 * Test method for {@link matrix.Indexes#stream(int, int)}.
	 */
	@Test
	public void testStreamIntInt() {
		Indexes to = new Indexes(3, 3);
        
		List<Indexes> twoIndexesResult = Indexes.stream(new Indexes(0,0), to).collect(Collectors.toList());
        List<Indexes> twoIntsResult = Indexes.stream(3,3).collect(Collectors.toList());
		
        assertEquals("Results from streams should be equal", twoIntsResult, twoIndexesResult);

	}

}
