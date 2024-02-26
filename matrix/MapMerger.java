package matrix;

import java.util.Comparator;
import java.util.Map.*;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;

final class MapMerger <K,V>{
	static <K,V> NavigableMap<K, V> merge(PeekingIterator<Entry<K,V>> itThis,PeekingIterator<Entry<K,V>> itOther, 
			Comparator<? super K> comparator, BinaryOperator<V> op, K origin, V zero){
		Objects.requireNonNull(itThis);
		Objects.requireNonNull(itOther);
		Objects.requireNonNull(comparator);
		Objects.requireNonNull(op);
		Objects.requireNonNull(origin);
		Objects.requireNonNull(zero);
		
		NavigableMap<K,V> tree = new TreeMap<>();
		MergeParameters<K,V> step = new MergeParameters<K,V>(origin, zero, zero);
		

		while(itThis.hasNext() || itOther.hasNext()) {
			
			step = presentIteratorMerge(itThis, itOther, comparator, zero, origin);
			tree.put(step.index(), op.apply(step.x(), step.y()));
		}
		return tree;	
	}
	private static <K,V> MergeParameters<K,V> stepParameters(PeekingIterator<Entry<K, V>> itThis, PeekingIterator<Entry<K, V>> itOther,
			Comparator<? super K> comparator, MergeParameters<K, V> mergeParameters) {
		assert(itThis != null);
		assert(itOther != null);
		
		if (MapMerger.isFirstIterator(itThis, itOther, comparator)) {		
			mergeParameters = stepParameters(itThis, mergeParameters::setX);
		}
		if(MapMerger.isFirstIterator(itOther, itThis, comparator)){
			mergeParameters = stepParameters(itOther, mergeParameters::setY);
		}
		return mergeParameters;
		
	}
	private static <K,V> MergeParameters<K,V> stepParameters(PeekingIterator <Entry<K, V>> iterator, Function<Entry<K, V>, MergeParameters<K, V>> parameters){
		assert iterator != null;
		assert iterator.hasNext();
		return parameters.apply(iterator.next());
	}
	//Helper Method for stepParameters, returns whether the first PeekingIterator has a smaller index
	private static <K,V> boolean isFirstIterator(PeekingIterator<Entry<K, V>> itThis, PeekingIterator<Entry<K, V>> itOther,
			Comparator<? super K> comparator) {
		return comparator.compare(itThis.element().getKey(), itOther.element().getKey()) <= 0;
	}
	//Helper method for merge, returns the step parameters for iterators with a next value
	private static <K,V> MergeParameters<K,V> presentIteratorMerge(PeekingIterator<Entry<K,V>> itThis, PeekingIterator<Entry<K,V>> itOther, Comparator<? super K> comparator,  V zero, K origin){
		MergeParameters<K,V> params= new MergeParameters<>(origin, zero, zero);
		
		if(itThis.hasNext() && itOther.hasNext()) {
			return stepParameters(itThis, itOther, comparator, params);
		}
		else if(itOther.hasNext()) {
			return stepParameters(itOther, n -> params.setX(n));
		}
		else {
			return stepParameters(itThis, n -> params.setY(n));
		}
	}
	



}
 