package com.estudo.estruturadados.lista;

interface PreviousIterator<T> {

    boolean hasPrevious();

    T previous();

    void remove();
}
