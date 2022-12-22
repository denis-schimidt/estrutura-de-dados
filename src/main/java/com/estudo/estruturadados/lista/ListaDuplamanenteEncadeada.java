package com.estudo.estruturadados.lista;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

public class ListaDuplamanenteEncadeada<T> implements Iterable<T>, Navigable<T> {
    private No<T> inicio;
    private No<T> fim;
    private int quantidadeNos;
    private ListaDuplamanenteEncadeadaIterator iterator;

    public void adicionarNoInicioDaLista(T elemento) {
        No<T> novoNo = new No<>(elemento);

        if (fim == null) {
            this.fim = novoNo;
        }

        if (inicio != null) {
            novoNo.setPosterior(this.inicio);
        }

        this.inicio = novoNo;
        quantidadeNos++;
    }

    public void adicionarNoFimDaLista(T elemento) {
        No<T> novoNo = new No<>(elemento);

        if (inicio == null) {
            this.inicio = novoNo;
        }

        if (this.fim != null) {
            novoNo.setAnterior(this.fim);
        }

        this.fim = novoNo;
        quantidadeNos++;
    }

    public int getSize() {
        return quantidadeNos;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, JSON_STYLE)
                .append("inicio", inicio)
                .append("fim", fim)
                .append("quantidadeNos", quantidadeNos)
                .toString();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        createIteratorWhenNecessary().forEachRemaining(action);
    }

    @Override
    public Iterator<T> iterator() {
        return createIteratorWhenNecessary();
    }

    @Override
    public Spliterator<T> spliterator() {
        return Spliterators.spliterator(createIteratorWhenNecessary(), getSize(), Spliterator.SIZED);
    }

    @Override
    public Optional<T> tryAdvance() {
        var iterator = createIteratorWhenNecessary();
        return Optional.ofNullable(iterator.hasNext() ? iterator.next() : null);
    }

    @Override
    public Optional<T> tryPrevious() {
        var iterator = createIteratorWhenNecessary();
        return Optional.ofNullable(iterator.hasPrevious() ? iterator.previous() : null);
    }

    private ListaDuplamanenteEncadeadaIterator<T> createIteratorWhenNecessary() {

        if (iterator == null) {
            iterator = new ListaDuplamanenteEncadeadaIterator<>();
        }

        return iterator;
    }

    private class ListaDuplamanenteEncadeadaIterator<E> implements Iterator<E>, PreviousIterator<E> {
        private No<T> corrente;

        @Override
        public boolean hasNext() {
            return ofNullable(corrente != null ? corrente : inicio)
                    .map(noCorrente -> noCorrente.getPosterior() != null)
                    .orElse(false);
        }

        @Override
        public E next() {
            if (corrente == null) {
                corrente = inicio;
                return (E) corrente.getElemento();
            }

            return ofNullable(corrente)
                    .map(noCorrente -> {
                        corrente = noCorrente.getPosterior();
                        return (E) corrente.getElemento();
                    }).orElse(null);
        }

        @Override
        public void remove() {
            if (corrente == null) {
                throw new IllegalStateException("É necessário utilizar o método next() antes de remover");
            }

            No<T> anterior = corrente.getAnterior();
            No<T> posterior = corrente.getPosterior();

            if (anterior != null) {
                anterior.setPosterior(posterior);
            }

            corrente.setElemento(null);
            corrente.setAnterior(null);
            corrente.setPosterior(null);
            corrente = null;

            inicio = posterior;
            quantidadeNos--;
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {

            while (hasNext()) {
                action.accept(next());
            }
        }

        @Override
        public boolean hasPrevious() {
            return ofNullable(corrente != null ? corrente : fim)
                    .map(noCorrente -> noCorrente.getAnterior() != null)
                    .orElse(false);
        }

        @Override
        public E previous() {
            if (corrente == null) {
                corrente = fim;
                return (E) corrente.getAnterior().getElemento();
            }

            return ofNullable(corrente)
                    .map(noCorrente -> {
                        corrente = noCorrente.getAnterior();
                        return (E) corrente.getElemento();
                    }).orElse(null);
        }
    }
}
