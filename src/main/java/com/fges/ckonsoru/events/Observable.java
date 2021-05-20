package com.fges.ckonsoru.events;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<T> {
    private final List<Observer<T>> observers = new ArrayList<>();

    public void subscribe(Observer<T> observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer<T> observer) {
        observers.remove(observer);
    }

    public void emit(T data) {
        for (Observer<T> observer : observers) {
            observer.trigger(data);
        }
    }
}
