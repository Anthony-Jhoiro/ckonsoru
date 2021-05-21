package com.fges.ckonsoru.events;

public interface Observer<T> {
    void trigger (T data);
}
