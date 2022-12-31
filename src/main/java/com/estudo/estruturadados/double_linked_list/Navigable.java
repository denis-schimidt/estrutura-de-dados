package com.estudo.estruturadados.double_linked_list;

import java.util.Optional;

interface Navigable<T> {

    Optional<T> tryAdvance();

    Optional<T> tryPrevious();
}
