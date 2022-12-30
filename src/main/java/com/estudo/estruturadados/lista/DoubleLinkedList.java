package com.estudo.estruturadados.lista;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.*;
import java.util.function.Consumer;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

public class DoubleLinkedList<T> implements Iterable<T>, Navigable<T> {
    private Node<T> inicialNode;
    private Node<T> finalNode;
    private int amountOfNodes;
    private DoubleLinkedListIterator iterator;

    public void insertAtBeginning(T value) {
        Node<T> newNode = new Node<>(value);

        if (finalNode == null) {
            this.finalNode = newNode;
        }

        if (inicialNode != null) {
            newNode.setAfter(this.inicialNode);
        }

        this.inicialNode = newNode;
        amountOfNodes++;
    }

    public boolean contains(T value) {
        return indexOf(value).isPresent();
    }

    public Optional<Integer> indexOf(T value) {
        int index = 0;
        Node<T> nodeAtual = inicialNode;

        while (nodeAtual != null) {

            if (nodeAtual.getValue().equals(value)) {
                return Optional.of(index);
            }

            nodeAtual = nodeAtual.getAfter();
            index++;
        }

        return Optional.empty();
    }

    public boolean insertAt(int index, T value) {

        if (index < 0 || index > amountOfNodes) {
            throw new IllegalArgumentException(format("It's NOT possible insert element (%s) at position %d", value, index));
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
        Node<T> currentNode = inicialNode;

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

        if (inicialNode == null) {
            this.inicialNode = novoNode;
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
        return new ToStringBuilder(this, JSON_STYLE)
                .append("inicialNode", inicialNode)
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

    private DoubleLinkedListIterator<T> getIterator() {

        if (iterator == null) {
            iterator = new DoubleLinkedListIterator<>();
        }

        return iterator;
    }

    public List<T> values() {
        var values = new ArrayList<T>();

        Node<T> nodeAtual = inicialNode;

        while (nodeAtual != null) {
            values.add(nodeAtual.getValue());
            nodeAtual = nodeAtual.getAfter();
        }

        return values;
    }

    private class DoubleLinkedListIterator<E> implements Iterator<E>, PreviousIterator<E> {
        private Node<T> currentNode;
        private boolean navigatingForward = true;

        @Override
        public boolean hasNext() {
            return ofNullable(currentNode != null ? currentNode : inicialNode)
                .map(nodeCorrente -> {

                    if (amountOfNodes > 1) {
                        return nodeCorrente.getAfter() != null;
                    }

                    return nodeCorrente != null;
                })
                .orElse(false);
        }

        @Override
        public E next() {
            this.navigatingForward = true;

            if (currentNode == null) {
                currentNode = inicialNode;
                return (E) currentNode.getValue();
            }

            return ofNullable(currentNode)
                    .map(nodeCorrente -> {
                        currentNode = nodeCorrente.getAfter();
                        return (E) currentNode.getValue();
                    }).orElse(null);
        }

        @Override
        public void remove() {

            if (currentNode == null) {
                throw new IllegalStateException("You must use the next() or previus() method at least once before removing");
            }

            Node<T> previousNode = currentNode.getBefore();
            Node<T> posteriorNode = currentNode.getAfter();

            if (previousNode != null) {
                previousNode.setAfter(posteriorNode);
            }

            adjustInicialOrFinalReferenceWhenNecessary(previousNode, posteriorNode);
            resetCurrentElement();

            amountOfNodes--;
        }

        private void adjustInicialOrFinalReferenceWhenNecessary(Node<T> previousNode, Node<T> posteriorNode) {

            if (navigatingForward) {

                if (inicialNode == currentNode) {
                    inicialNode = posteriorNode;
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
        public void forEachRemaining(Consumer<? super E> action) {

            while (hasNext()) {
                action.accept(next());
            }
        }

        @Override
        public boolean hasPrevious() {
            return ofNullable(currentNode != null ? currentNode : finalNode)
                .map(node -> {
                    if (amountOfNodes > 1) {
                        return node.getBefore() != null;
                    }

                    return node != null;
                })
                .orElse(false);
        }

        @Override
        public E previous() {
            this.navigatingForward = false;

            if (currentNode == null) {
                currentNode = finalNode;
                return (E) currentNode.getValue();
            }

            return ofNullable(currentNode)
                    .map(nodeCorrente -> {
                        currentNode = nodeCorrente.getBefore();
                        return (E) currentNode.getValue();
                    }).orElse(null);
        }
    }
}
