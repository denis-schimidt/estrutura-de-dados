package com.estudo.estruturadados.stack;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Stack<T> {
    private List<T> elements = new LinkedList<>();

    private Iterator<T> iterator;

    public int size() {
        return elements.size();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public boolean contains(T value) {
        return elements.contains(value);
    }

    public Iterator<T> iterator() {

        if (iterator == null) {
            iterator = new StackIterator();
        }

        return iterator;
    }

    public boolean push(T value) {
        return elements.add(value);
    }

    public Optional<T> pop() {
        return Optional.ofNullable(elements.isEmpty() ? null : elements.remove(getLastIndex()));
    }

    public Optional<T> peek() {
        return Optional.ofNullable(elements.isEmpty() ? null : elements.get(getLastIndex()));
    }

    private int getLastIndex() {
        return elements.size() - 1;
    }

    public List<T> toList() {
        List<T> list = elements.stream().collect(Collectors.toList());
        Collections.reverse(list);

        return list;
    }

    private class StackIterator implements Iterator<T> {
        private int currentIndex = elements.size() - 1;

        @Override
        public boolean hasNext() {
            return currentIndex >= 0;
        }

        @Override
        public T next() {
            T value = elements.get(currentIndex);
            currentIndex--;

            return value;
        }

        @Override
        public void remove() {
            pop().orElseThrow(IllegalStateException::new);
            currentIndex--;
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {

            while (hasNext()) {
                action.accept(next());
            }
        }
    }
}
