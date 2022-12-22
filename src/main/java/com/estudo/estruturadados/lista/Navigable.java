package com.estudo.estruturadados.lista;

import java.util.Optional;

public interface Navigable<T> {

    Optional<T> tryAdvance();

    Optional<T> tryPrevious();
}
