package org.example;

@FunctionalInterface
public interface Func<K, V, T, R> {
    R proceed(K k, V v, T t);
}
