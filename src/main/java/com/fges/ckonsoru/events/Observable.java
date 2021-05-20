package com.fges.ckonsoru.events;

public interface Observable<T> {
    void subscribe(Observer<T> observer);
    void unsubscribe(Observer<T> observer);
    void emit(T data);
}
