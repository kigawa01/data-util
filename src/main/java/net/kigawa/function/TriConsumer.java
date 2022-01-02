package net.kigawa.function;

public interface TriConsumer<T, U, V> {
    void accept(T t, U u, V v);
}
