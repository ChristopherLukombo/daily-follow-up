package fr.almavivahealth.util.functional;

import java.util.Collection;

import fr.almavivahealth.exception.FunctionalException;

/**
 * Represents a function that accepts one argument and does not return any value;
 * Function might throw a checked exception instance.
 *
 * @param <T> the type of the input to the function
 * @param <E> the type of the thrown checked exception
 *
 * @author christopher
 */
@FunctionalInterface
public interface LoopWithIndexAndSizeConsumer<T, E extends Exception> {

	void accept(T t, int i, int n) throws E;

	static <T> void forEach(
			final Collection<T> collection,
			final LoopWithIndexAndSizeConsumer<T, Exception> consumer) {
		try {
			int index = 0;
			for (final T object : collection) {
				consumer.accept(object, index++, collection.size());
			}
		} catch (final Exception e) {
			throw new FunctionalException(e);
		}
	}
}
