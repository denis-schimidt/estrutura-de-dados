package com.estudo.estruturadados.lista;

import java.util.Optional;

interface Navigable<T> {

    Optional<T> tryAdvance();

    Optional<T> tryPrevious();
}
