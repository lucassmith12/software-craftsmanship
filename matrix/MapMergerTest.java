package matrix;

import static org.junit.Assert.*;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.BinaryOperator;

import org.junit.Test;

public class MapMergerTest {

	@Test
	public void testMerge() {
		Map <Integer, Integer> tester1 = new TreeMap<>();
		tester1.put(1, 1);
		tester1.put(2, 2);
		tester1.put(3, 3);
		tester1.put(5, 5);
		
		Map <Integer, Integer> tester2 = new TreeMap<>();
		tester2.put(1, 1);
		tester2.put(2, 2);
		tester2.put(3, 3);
		
		BinaryOperator<Integer> add = (a, b) -> a + b;
		NavigableMap<Integer, Integer> mergedMap = MapMerger.merge(PeekingIterator.from(
				tester1.entrySet().iterator()), PeekingIterator.from(tester2.entrySet().iterator()),Comparator.naturalOrder(), 
				add, Integer.valueOf(0), Integer.valueOf(0));
		for(Entry<Integer, Integer> e: mergedMap.entrySet()) {
			if(e.getKey()<4) {
				assert e.getValue() == e.getKey()*2;
			}
			else {
				assert e.getKey() == e.getValue();
			}
		}
		
	}

}
