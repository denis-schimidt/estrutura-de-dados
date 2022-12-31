package com.estudo.estruturadados.double_linked_list;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.*;
import java.util.function.Consumer;

public class DoubleLinkedList<T> implements Iterable<T>, Navigable<T> {
    private Node<T> initialNode;
    private Node<T> finalNode;
    private int amountOfNodes;
    private DoubleLinkedListIterator iterator;

    public void insertAtBeginning(T value) {
        Node<T> newNode = new Node<>(value);

        if (finalNode == null) {
            this.finalNode = newNode;
        }

        if (initialNode != null) {
            newNode.setAfter(this.initialNode);
        }

        this.initialNode = newNode;
        amountOfNodes++;
    }

    public boolean contains(T value) {
        return indexOf(value).isPresent();
    }

    public Optional<Integer> indexOf(T value) {
        int index = 0;
        Node<T> currentNode = initialNode;

        while (currentNode != null) {

            if (currentNode.getValue().equals(value)) {
                return Optional.of(index);
            }

            currentNode = currentNode.getAfter();
            index++;
        }

        return Optional.empty();
    }

    public boolean insertAt(int index, T value) {

        if (index < 0 || index > amountOfNodes) {
            throw new IllegalArgumentException(String.format("It's NOT possible insert element (%s) at position %d", value, index));
        }

        if (index == amountOfNodes) {
            insertAtEnd(value);
            return true;

        } else if (index == 0) {
            insertAtBeginning(value);
            return true;
        }

        return insertInMiddleOfTheList(index, value);
    }

    private boolean insertInMiddleOfTheList(int index, T value) {
        int currentIndex = 0;
        Node<T> currentNode = initialNode;

        while (currentNode != null) {

            if (currentIndex == index) {
                Node<T> newNode = new Node<>(value);
                newNode.setBefore(currentNode.getBefore());
                newNode.setAfter(currentNode);
                amountOfNodes++;

                return true;
            }

            currentNode = currentNode.getAfter();
            currentIndex++;
        }

        return false;
    }

    public void insertAtEnd(T value) {
        Node<T> novoNode = new Node<>(value);

        if (initialNode == null) {
            this.initialNode = novoNode;
        }

        if (this.finalNode != null) {
            novoNode.setBefore(this.finalNode);
        }

        this.finalNode = novoNode;
        amountOfNodes++;
    }

    public int getSize() {
        return amountOfNodes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("initialNode", initialNode)
                .append("finalNode", finalNode)
                .append("amountOfNodes", amountOfNodes)
                .toString();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        getIterator().forEachRemaining(action);
    }

    @Override
    public Iterator<T> iterator() {
        return getIterator();
    }

    @Override
    public Spliterator<T> spliterator() {
        return Spliterators.spliterator(getIterator(), getSize(), Spliterator.SIZED);
    }

    @Override
    public Optional<T> tryAdvance() {
        var iterator = getIterator();
        return Optional.ofNullable(iterator.hasNext() ? iterator.next() : null);
    }

    @Override
    public Optional<T> tryPrevious() {
        var iterator = getIterator();
        return Optional.ofNullable(iterator.hasPrevious() ? iterator.previous() : null);
    }

    private DoubleLinkedListIterator getIterator() {

        if (iterator == null) {
            iterator = new DoubleLinkedListIterator();
        }

        return iterator;
    }

    public List<T> values() {
        var values = new ArrayList<T>();

        Node<T> currentNode = initialNode;

        while (currentNode != null) {
            values.add(currentNode.getValue());
            currentNode = currentNode.getAfter();
        }

        return values;
    }

    private class DoubleLinkedListIterator implements Iterator<T>, PreviousIterator<T> {
        private Node<T> currentNode;
        private boolean navigatingForward = true;

        @Override
        public boolean hasNext() {
            return Optional.ofNullable(currentNode != null ? currentNode : initialNode)
                .map(node -> {

                    if (amountOfNodes > 1) {
                        return node.getAfter() != null;
                    }

                    return true;
                })
                .orElse(false);
        }

        @Override
        public T next() {
            this.navigatingForward = true;

            if (currentNode == null) {
                currentNode = initialNode;
                return currentNode.getValue();
            }

            return Optional.of(currentNode)
                    .map(node -> {
                        currentNode = node.getAfter();
                        return currentNode.getValue();
                    }).orElse(null);
        }

        @Override
        public void remove() {

            if (currentNode == null) {
                throw new IllegalStateException("You must use the next() or previous() method at least once before removing");
            }

            Node<T> previousNode = currentNode.getBefore();
            Node<T> posteriorNode = currentNode.getAfter();

            if (previousNode != null) {
                previousNode.setAfter(posteriorNode);
            }

            adjustInitialOrFinalReferenceWhenNecessary(previousNode, posteriorNode);
            resetCurrentElement();

            amountOfNodes--;
        }

        private void adjustInitialOrFinalReferenceWhenNecessary(Node<T> previousNode, Node<T> posteriorNode) {

            if (navigatingForward) {

                if (initialNode == currentNode) {
                    initialNode = posteriorNode;
                }

            } else if (finalNode == currentNode) {
                finalNode = previousNode;
            }
        }

        private void resetCurrentElement() {
            currentNode.setValue(null);
            currentNode.setBefore(null);
            currentNode.setAfter(null);
            currentNode = null;
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {

            while (hasNext()) {
                action.accept(next());
            }
        }

        @Override
        public boolean hasPrevious() {
            return Optional.ofNullable(currentNode != null ? currentNode : finalNode)
                .map(node -> {
                    if (amountOfNodes > 1) {
                        return node.getBefore() != null;
                    }

                    return true;
                })
                .orElse(false);
        }

        @Override
        public T previous() {
            this.navigatingForward = false;

            if (currentNode == null) {
                currentNode = finalNode;
                return currentNode.getValue();
            }

            return Optional.of(currentNode)
                    .map(node -> {
                        currentNode = node.getBefore();
                        return currentNode.getValue();
                    }).orElse(null);
        }
    }
}
