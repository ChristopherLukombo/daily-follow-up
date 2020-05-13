package fr.almavivahealth.util.functional;

@FunctionalInterface
public interface LoopWithIndexAndSizeConsumer<T, E extends Exception> {
	void accept(T t, int i, int n) throws E;
}
