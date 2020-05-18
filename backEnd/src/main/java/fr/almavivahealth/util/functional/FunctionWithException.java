package fr.almavivahealth.util.functional;
import java.util.function.Function;

import fr.almavivahealth.exception.FunctionalException;

/**
 * Represents a function that accepts one argument and return value;
 * Function might throw a checked exception instance.
 *
 * @param <T> the type of the input to the function
 * @param <E> the type of the thrown checked exception
 *
 * @author christopher
 */
@FunctionalInterface
public interface FunctionWithException<T, R, E extends Exception> {

	R apply(T t) throws E;

	static <T, R, E extends Exception> Function<T, R> wrapper(final FunctionWithException<T, R, E> fe) {
		return arg -> {
			try {
				return fe.apply(arg);
			} catch (final Exception e) {
				throw new FunctionalException(e);
			}
		};
	}
}
