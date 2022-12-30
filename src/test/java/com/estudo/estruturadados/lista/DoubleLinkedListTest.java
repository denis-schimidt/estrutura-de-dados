package com.estudo.estruturadados.lista;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class DoubleLinkedListTest {

    @ParameterizedTest
    @MethodSource("createDoubleLinkedList")
    public void deveAdicionar4ItensNaFimDaListaERetornarTamanhoDaListaEValoresNaOrdemCorreta(DoubleLinkedList<Integer> list) {
        assertEquals(4, list.getSize());
        assertArrayEquals(new int[]{1, 3, 9, 15},
            new int[]{
                    list.tryAdvance().get(),
                    list.tryAdvance().get(),
                    list.tryAdvance().get(),
                    list.tryAdvance().get()
            });
    }

    @Test
    public void deveAdicionar4ItensNaInicioDaListaERetornarTamanhoDaListaEValoresNaOrdemCorreta() {
        DoubleLinkedList<Integer> list = new DoubleLinkedList<>();
        list.addToTopOfList(1);
        list.addToTopOfList(3);
        list.addToTopOfList(9);
        list.addToTopOfList(15);

        assertEquals(4, list.getSize());
        assertArrayEquals(new Integer[]{15, 9, 3, 1},
            new Integer[]{
                    list.tryAdvance().get(),
                    list.tryAdvance().get(),
                    list.tryAdvance().get(),
                    list.tryAdvance().get()
            });
    }

    @ParameterizedTest
    @MethodSource("createDoubleLinkedList")
    public void deveNavegarCorretamenteDoFimParaInicioDaLista(DoubleLinkedList<Integer> list) {
        assertArrayEquals(new int[]{15, 9, 3, 1},
                new int[]{
                        list.tryPrevious().get(),
                        list.tryPrevious().get(),
                        list.tryPrevious().get(),
                        list.tryPrevious().get()
                });
    }

    @ParameterizedTest
    @MethodSource("createDoubleLinkedList")
    public void deveRemoverTodosOsItensDaListaIterandoParaFrente(DoubleLinkedList<Integer> list) {
        Iterator<Integer> iterator = list.iterator();

        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }

        assertEquals(0, list.getSize());
    }

    @ParameterizedTest
    @MethodSource("createDoubleLinkedList")
    public void deveRemoverTodosOsItensDaListaIterandoParaTras(DoubleLinkedList<Integer> list) {
        PreviousIterator<Integer> iterator = (PreviousIterator) list.iterator();

        while (iterator.hasPrevious()) {
            iterator.previous();
            iterator.remove();
        }

        assertEquals(0, list.getSize());
    }

    @ParameterizedTest
    @MethodSource("createDoubleLinkedList")
    public void deveRemoverUmItemDoMeioDaListaParaFrente(DoubleLinkedList<Integer> list) {
        Iterator<Integer> iterator = list.iterator();

        while (iterator.hasNext()) {

            if (iterator.next() == 9) {
                iterator.remove();
            }
        }

        assertIterableEquals(List.of(1, 3, 15), list.values());
        assertEquals(3, list.getSize());
    }

    @ParameterizedTest
    @MethodSource("createDoubleLinkedList")
    public void deveRemoverUmItemDoMeioDaListaParaTras(DoubleLinkedList<Integer> list) {
        PreviousIterator<Integer> iterator = (PreviousIterator<Integer>) list.iterator();

        while (iterator.hasPrevious()) {

            if (iterator.previous() == 9) {
                iterator.remove();
            }
        }

        assertIterableEquals(List.of(1, 3, 15), list.values());
        assertEquals(3, list.getSize());
    }

    @ParameterizedTest
    @MethodSource("createDoubleLinkedList")
    public void deveRetornarTrueQuandoExisteUmValorNaLista(DoubleLinkedList<Integer> list) {
        assertTrue(list.contains(9));
    }

    @ParameterizedTest
    @MethodSource("createDoubleLinkedList")
    public void deveRetornarFalseQuandoNãoExisteUmValorNaLista(DoubleLinkedList<Integer> list) {
        assertFalse(list.contains(0));
    }

    private static Stream<Arguments> createDoubleLinkedList() {
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList<Integer>();
        doubleLinkedList.addToEndOfList(1);
        doubleLinkedList.addToEndOfList(3);
        doubleLinkedList.addToEndOfList(9);
        doubleLinkedList.addToEndOfList(15);

        return Stream.of(Arguments.of(doubleLinkedList));
    }
}
