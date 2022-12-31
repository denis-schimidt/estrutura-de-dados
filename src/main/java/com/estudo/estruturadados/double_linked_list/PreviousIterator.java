package com.estudo.estruturadados.double_linked_list;

interface PreviousIterator<T> {

    boolean hasPrevious();

    T previous();

    void remove();
}
