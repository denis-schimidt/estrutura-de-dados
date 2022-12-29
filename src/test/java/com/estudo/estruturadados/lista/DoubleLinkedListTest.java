package com.estudo.estruturadados.lista;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DoubleLinkedListTest {

    private DoubleLinkedList<Integer> doubleLinkedList;

    @BeforeEach
    public void setUp() {
        doubleLinkedList = new DoubleLinkedList();
    }

    @Test
    public void deveAdicionar4ItensNaFimDaListaERetornarTamanhoDaListaEValoresNaOrdemCorreta() {
        doubleLinkedList.addToTopOfList(1);
        doubleLinkedList.addToTopOfList(3);
        doubleLinkedList.addToTopOfList(9);
        doubleLinkedList.addToTopOfList(15);

        assertEquals(4, doubleLinkedList.getSize());
        assertArrayEquals(new int[]{1, 3, 9, 15},
                new int[]{
                        doubleLinkedList.tryAdvance().get(),
                        doubleLinkedList.tryAdvance().get(),
                        doubleLinkedList.tryAdvance().get(),
                        doubleLinkedList.tryAdvance().get()
                });
    }

    @Test
    public void deveAdicionar4ItensNaInicioDaListaERetornarTamanhoDaListaEValoresNaOrdemCorreta() {
        doubleLinkedList.addToEndOfList(1);
        doubleLinkedList.addToEndOfList(3);
        doubleLinkedList.addToEndOfList(9);
        doubleLinkedList.addToEndOfList(15);

        assertEquals(4, doubleLinkedList.getSize());
        assertArrayEquals(new int[]{15, 9, 3, 1},
                new int[]{
                        doubleLinkedList.tryAdvance().get(),
                        doubleLinkedList.tryAdvance().get(),
                        doubleLinkedList.tryAdvance().get(),
                        doubleLinkedList.tryAdvance().get()
                });
    }

    @Test
    public void deveNavegarCorretamenteDoFimParaInicioDaLista() {
        doubleLinkedList.addToTopOfList(1);
        doubleLinkedList.addToTopOfList(3);
        doubleLinkedList.addToTopOfList(9);
        doubleLinkedList.addToTopOfList(15);

        assertArrayEquals(new int[]{15, 9, 3, 1},
                new int[]{
                        doubleLinkedList.tryPrevious().get(),
                        doubleLinkedList.tryPrevious().get(),
                        doubleLinkedList.tryPrevious().get(),
                        doubleLinkedList.tryPrevious().get()
                });
    }

    @Test
    public void deveRemoverTodosOsItensDaListaIterandoParaFrente() {
        doubleLinkedList.addToTopOfList(1);
        doubleLinkedList.addToTopOfList(3);
        doubleLinkedList.addToTopOfList(9);
        doubleLinkedList.addToTopOfList(15);

        Iterator<Integer> iterator = doubleLinkedList.iterator();

        while (iterator.hasNext()) {
            var elemento = iterator.next();
            iterator.remove();
        }

        assertEquals(0, doubleLinkedList.getSize());
    }

    @Test
    public void deveRemoverTodosOsItensDaListaIterandoParaTras() {
        doubleLinkedList.addToTopOfList(1);
        doubleLinkedList.addToTopOfList(3);
        doubleLinkedList.addToTopOfList(9);
        doubleLinkedList.addToTopOfList(15);

        PreviousIterator<Integer> iterator = (PreviousIterator) doubleLinkedList.iterator();

        while (iterator.hasPrevious()) {
            var elemento = iterator.previous();
            iterator.remove();
        }

        assertEquals(0, doubleLinkedList.getSize());
    }

    @Test
    public void deveRemoverUmItemDoMeioDaListaParaFrente() {
        doubleLinkedList.addToTopOfList(1);
        doubleLinkedList.addToTopOfList(3);
        doubleLinkedList.addToTopOfList(9);
        doubleLinkedList.addToTopOfList(15);

        Iterator<Integer> iterator = doubleLinkedList.iterator();

        while (iterator.hasNext()) {

            if (iterator.next() == 9) {
                iterator.remove();
            }
        }

        assertIterableEquals(List.of(1, 3, 15), doubleLinkedList.values());
        assertEquals(3, doubleLinkedList.getSize());
    }

    @Test
    public void deveRemoverUmItemDoMeioDaListaParaTras() {
        doubleLinkedList.addToTopOfList(1);
        doubleLinkedList.addToTopOfList(3);
        doubleLinkedList.addToTopOfList(9);
        doubleLinkedList.addToTopOfList(15);

        PreviousIterator<Integer> iterator = (PreviousIterator<Integer>) doubleLinkedList.iterator();

        while (iterator.hasPrevious()) {

            if (iterator.previous() == 9) {
                iterator.remove();
            }
        }

        assertIterableEquals(List.of(1, 3, 15), doubleLinkedList.values());
        assertEquals(3, doubleLinkedList.getSize());
    }
}
