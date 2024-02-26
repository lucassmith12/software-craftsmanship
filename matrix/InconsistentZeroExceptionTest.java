package matrix;

import static org.junit.Assert.*;

import java.util.TreeMap;
import java.util.function.BinaryOperator;

import org.junit.Test;

public class InconsistentZeroExceptionTest<I,T> {

	@Test
	public void testRequireMatching() {
		
		
		Matrix<Indexes, Integer> integers = NavigableMatrix.constant(3, 3, 2, 0);
		Matrix<Indexes, Double> doubles = NavigableMatrix.constant(3, 3, 2.0, 0.0);
		Integer[][] inputMatrix = {
				{1, 2, 3},
				{4, 5, 6},
				{7, 8, 9}
		};
		BinaryOperator<Integer> add = (a, b) -> a + b;
		//Generics not cooperating here
		assertThrows(InconsistentZeroException.class, InconsistentZeroException.requireMatching(doubles, integers));
	}

}
