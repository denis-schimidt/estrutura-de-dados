package com.estudo.estruturadados.lista;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListaDuplamanenteEncadeadaTest {

    private ListaDuplamanenteEncadeada<Integer> listaDuplamanenteEncadeada;

    @BeforeEach
    public void setUp() {
        listaDuplamanenteEncadeada = new ListaDuplamanenteEncadeada();
    }

    @Test
    public void deveAdicionar4ItensNaFimDaListaERetornarTamanhoDaListaEValoresNaOrdemCorreta() {
        listaDuplamanenteEncadeada.adicionarNoFimDaLista(1);
        listaDuplamanenteEncadeada.adicionarNoFimDaLista(3);
        listaDuplamanenteEncadeada.adicionarNoFimDaLista(9);
        listaDuplamanenteEncadeada.adicionarNoFimDaLista(15);

        assertEquals(4, listaDuplamanenteEncadeada.getSize());
        assertArrayEquals(new int[]{1, 3, 9, 15},
                new int[]{
                        listaDuplamanenteEncadeada.tryAdvance().get(),
                        listaDuplamanenteEncadeada.tryAdvance().get(),
                        listaDuplamanenteEncadeada.tryAdvance().get(),
                        listaDuplamanenteEncadeada.tryAdvance().get()
                });
    }

    @Test
    public void deveAdicionar4ItensNaInicioDaListaERetornarTamanhoDaListaEValoresNaOrdemCorreta() {
        listaDuplamanenteEncadeada.adicionarNoInicioDaLista(1);
        listaDuplamanenteEncadeada.adicionarNoInicioDaLista(3);
        listaDuplamanenteEncadeada.adicionarNoInicioDaLista(9);
        listaDuplamanenteEncadeada.adicionarNoInicioDaLista(15);

        assertEquals(4, listaDuplamanenteEncadeada.getSize());
        assertArrayEquals(new int[]{15,9,3,1},
                new int[]{
                        listaDuplamanenteEncadeada.tryAdvance().get(),
                        listaDuplamanenteEncadeada.tryAdvance().get(),
                        listaDuplamanenteEncadeada.tryAdvance().get(),
                        listaDuplamanenteEncadeada.tryAdvance().get()
                });
    }

    @Test
    public void deveNavegarCorretamenteDoFimParaInicioDaLista() {
        listaDuplamanenteEncadeada.adicionarNoFimDaLista(1);
        listaDuplamanenteEncadeada.adicionarNoFimDaLista(3);
        listaDuplamanenteEncadeada.adicionarNoFimDaLista(9);
        listaDuplamanenteEncadeada.adicionarNoFimDaLista(15);

        assertArrayEquals(new int[]{15,9,3,1},
                new int[]{
                        listaDuplamanenteEncadeada.tryPrevious().get(),
                        listaDuplamanenteEncadeada.tryPrevious().get(),
                        listaDuplamanenteEncadeada.tryPrevious().get(),
                        listaDuplamanenteEncadeada.tryPrevious().get()
                });
    }

    @Test
    public void deveRemoverTodosOsItensDaListaIterandoParaFrente() {
        listaDuplamanenteEncadeada.adicionarNoFimDaLista(1);
        listaDuplamanenteEncadeada.adicionarNoFimDaLista(3);
        listaDuplamanenteEncadeada.adicionarNoFimDaLista(9);
        listaDuplamanenteEncadeada.adicionarNoFimDaLista(15);

        Iterator<Integer> iterator = listaDuplamanenteEncadeada.iterator();

        while(iterator.hasNext()){
            var elemento = iterator.next();
            iterator.remove();
        }

        assertEquals(0, listaDuplamanenteEncadeada.getSize());
    }

    @Test
    public void deveRemoverTodosOsItensDaListaIterandoParaTras() {
        listaDuplamanenteEncadeada.adicionarNoFimDaLista(1);
        listaDuplamanenteEncadeada.adicionarNoFimDaLista(3);
        listaDuplamanenteEncadeada.adicionarNoFimDaLista(9);
        listaDuplamanenteEncadeada.adicionarNoFimDaLista(15);

        PreviousIterator<Integer> iterator = (PreviousIterator) listaDuplamanenteEncadeada.iterator();

        while(iterator.hasPrevious()){
            var elemento = iterator.previous();
            iterator.remove();
        }

        assertEquals(0, listaDuplamanenteEncadeada.getSize());
    }
}
