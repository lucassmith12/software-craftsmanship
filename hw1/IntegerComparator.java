package hw1;

import java.util.Comparator;

public class IntegerComparator implements Comparator<Integer> {
	public IntegerComparator() {}
	
	public int compare(Integer int1, Integer int2) {
		return int1.intValue() - int2.intValue();
	}
}

