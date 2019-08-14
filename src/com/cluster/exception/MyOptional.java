package com.cluster.exception;

import java.util.NoSuchElementException;

public class MyOptional<T> {
    private T value;

    public MyOptional(T value) {
        this.value = value;
    }

    public T get() {
        if (value == null) throw new NoSuchElementException("No value present");
        return value;
    }

    public T orElse(T other) {
        return value == null ? other : value;
    }

    public boolean isPresent() {
        return value != null;
    }
}
