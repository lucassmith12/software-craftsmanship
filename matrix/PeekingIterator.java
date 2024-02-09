package matrix;

import java.util.Iterator;
import java.util.Optional;

public final class PeekingIterator<E> implements Iterator<E> {
	private Iterator<E> it;
	private Optional<E> next;

	private PeekingIterator(Iterator<E> iter){
		it = iter;
		next = Optional.of(iter.next());
	}

	public static <E> PeekingIterator<E> from(Iterator<E> iter) {
		return new PeekingIterator<E>(iter);
	}
	public static <E> PeekingIterator<E> from(Iterable<E> iter) {
		return new PeekingIterator<E>(iter.iterator());
	}
	public boolean hasNext() {
		if(next.equals(null)) return false;
		return true;
	}

	
	public E next() {
		return it.next();
	}
	
	public Optional<E> peek() {
		return next;
	}
	
	public E element() {
		return next.get();
	}
	

}
