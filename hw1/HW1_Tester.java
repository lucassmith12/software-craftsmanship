package hw1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class HW1_Tester {

	public static void main(String[] args) {
		List<Integer> list1 = new LinkedList<>();
		List<Integer> list2 = new ArrayList<>();
		Comparator<Integer> cmp = new IntegerComparator();
		
		for(int i = 0; i<8; i++) list1.add((int)(Math.random()*100));
		for(int i = 0; i<8; i++) list2.add((int)(Math.random()*100));
		
		System.out.println("List1: " + list1.toString());
		System.out.println("List2: " + list2.toString());
		System.out.println("Longest Higher Suffix: " + CS293_HW1_LJS174.longestHigherSuffix(list1, list2, cmp));
	}

}
