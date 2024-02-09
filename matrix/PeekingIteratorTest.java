package matrix;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

public class PeekingIteratorTest {

	@Test
	public void testPeek() {
		
		Map<Indexes, Integer> tree = Map.of(new Indexes (0,0), 3, new Indexes (0,1), 2);
		PeekingIterator<Map.Entry<Indexes, Integer>> peekIter = PeekingIterator.from(tree.entrySet().iterator());
		assertEquals("Peek should return the correct element", peekIter.peek().get().getValue().equals(Integer.valueOf(2)), true);
		assertEquals("Peek should not remove any elements", tree.size() == 2, true);
	}

	@Test
	public void testElement() {
		Map<Indexes, Integer> tree = Map.of(new Indexes (0,0), 3, new Indexes (0,1), 2);
		PeekingIterator<Map.Entry<Indexes, Integer>> peekIter = PeekingIterator.from(tree.entrySet().iterator());
		assertEquals("Element should return the correct element", peekIter.element().getValue().equals(Integer.valueOf(3)), true);
	}

}
