package matrix;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

public final class PeekingIterator<E> implements Iterator<E> {
	private Iterator<E> it;
	private Optional<E> next;

	private PeekingIterator(Iterator<E> iter){
		it = iter;
		next = Optional.of(iter.next());
	}

	public static <E> PeekingIterator<E> from(Iterator<E> iter) {
		return new PeekingIterator<E>(Objects.requireNonNull(iter));
	}
	public static <E> PeekingIterator<E> from(Iterable<E> iter) {
		return new PeekingIterator<E>(Objects.requireNonNull(iter).iterator());
	}
	@Override
	public boolean hasNext() {
		return next.isPresent();
	}

	@Override
	public E next() {
		E e = next.get();
		if(it.hasNext()) {
			next = Optional.of(it.next());
		}
		else {
			next = Optional.empty();
		}
		return e;
	}
	
	public Optional<E> peek() {
		return next;
	}
	
	public E element() {
		return next.get();
	}
	

}
