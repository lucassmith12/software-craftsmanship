package hw1;
import java.util.Comparator;
import java.util.List;

/**
 * <h2>Assignment 1 -- Write a method finds the longest highest suffix of two lists</h2>
 * A suffix is the element(s) at the end of a list 
 * <br>
 * The longest, highest suffix of two lists is the suffix of list a whose 
 * elements are greater than or equal to the corresponding element in list b
 * 
 * @author	Lucas Smith
 * @version	2.0
 * @since 	2024-01-26
 */
public class CS293_HW1_LJS174 {
	/*
	 * Helper method
	 * During recitation: removed unnecessary helper methods
	 */
	private static <T> boolean secondSuffixIsLarger(List<T> listA, List<T> listB, Comparator<T> comparator) {
		return comparator.compare(listA.get(listA.size()-1), listB.get(listB.size()-1))<0;
	}
	/**
	 * Recursive function that finds the longest highest suffix of two lists
	 * @param 	biggerList 			The list with the higher suffix
	 * @param 	smallerList			The list with the lower suffix
	 * @param 	cmp 				Comparator instance
	 * @param	suffixesAreEqual 	Keeps track of whether or not the suffixes have been equal so far
	 * @return 	Longest highest suffix
	 */
	private static <T extends Comparable<? super T>> List<T> listBuilder(List<T> biggerList, List<T> smallerList, Comparator <T> cmp){
		/* Base Cases: Return an empty list if either list is empty, or if the list with the greater suffix 
		 * has an element less than that of the other list at the same index
		*/
		if(biggerList.isEmpty() || smallerList.isEmpty()) {
			return biggerList.subList(0, 0);
		}
		if(secondSuffixIsLarger(biggerList, smallerList, cmp)) {
			return biggerList.subList(0, 0);
		}
		/*
		 * Recursive call
		 * During recitation: removed unnecessary check of which array is larger
		 */
		smallerList.remove(smallerList.size()-1);
		T suffixElement = biggerList.remove(biggerList.size()-1);
		
		List<T> suffix = listBuilder(biggerList, smallerList, cmp);
		suffix.add(suffixElement);
		return suffix;
	}
	public static <T extends Comparable<? super T>> List<T> longestHigherSuffix(List<T> a, List<T> b, Comparator<T> cmp){
		return listBuilder(a,b,cmp);
	}
}
