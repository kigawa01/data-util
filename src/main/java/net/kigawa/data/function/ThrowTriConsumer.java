package net.kigawa.data.function;

public interface ThrowTriConsumer<T, U, V> {
    void accept(T t, U u, V v) throws Exception;
}
