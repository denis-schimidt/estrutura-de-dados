package com.estudo.estruturadados.lista;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

public class ListaDuplamanenteEncadeada<T> implements Iterable<T> {
    private No<T> inicio;
    private No<T> fim;
    private int quantidadeNos;

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
        new ListaDuplamanenteIterator<T>()
                .forEachRemaining(action);
    }

    @Override
    public Iterator<T> iterator() {
        return new ListaDuplamanenteIterator<T>();
    }

    @Override
    public Spliterator<T> spliterator() {
        return Spliterators.spliteratorUnknownSize(new ListaDuplamanenteIterator<>(), 0);
    }

    private class ListaDuplamanenteIterator<E> implements Iterator<E> {
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
    }
}
