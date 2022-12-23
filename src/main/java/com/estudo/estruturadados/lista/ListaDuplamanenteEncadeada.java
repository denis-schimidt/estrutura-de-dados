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

    private ListaDuplamanenteEncadeadaIterator<T> getIterator() {

        if (iterator == null) {
            iterator = new ListaDuplamanenteEncadeadaIterator<>();
        }

        return iterator;
    }

    private class ListaDuplamanenteEncadeadaIterator<E> implements Iterator<E>, PreviousIterator<E> {
        private No<T> corrente;
        private boolean navegandoParaFrente = true;

        @Override
        public boolean hasNext() {
            return ofNullable(corrente != null ? corrente : inicio)
                    .map(noCorrente -> {

                        if (quantidadeNos > 1) {
                            return noCorrente.getPosterior() != null;
                        }

                        return noCorrente != null;
                    })
                    .orElse(false);
        }

        @Override
        public E next() {
            this.navegandoParaFrente = true;

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
                throw new IllegalStateException("É necessário utilizar ao menos uma vez o método next() ou previus() antes de remover");
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
            // TODO Verificar remoção para trás
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
                    .map(noCorrente -> {
                        if (quantidadeNos > 1) {
                            return noCorrente.getAnterior() != null;
                        }

                        return noCorrente != null;
                    })
                    .orElse(false);
        }

        @Override
        public E previous() {
            this.navegandoParaFrente = false;

            if (corrente == null) {
                corrente = fim;
                return (E) corrente.getElemento();
            }

            return ofNullable(corrente)
                    .map(noCorrente -> {
                        corrente = noCorrente.getAnterior();
                        return (E) corrente.getElemento();
                    }).orElse(null);
        }
    }
}
