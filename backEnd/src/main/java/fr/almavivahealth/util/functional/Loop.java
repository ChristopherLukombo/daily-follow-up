package fr.almavivahealth.util.functional;

import java.util.Collection;

import fr.almavivahealth.exception.FunctionalException;

public final class Loop {

	private Loop() {
		throw new UnsupportedOperationException();
	}

	public static <T> void forEach(
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
