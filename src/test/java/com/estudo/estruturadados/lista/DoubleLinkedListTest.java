package com.estudo.estruturadados.lista;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DoubleLinkedListTest {

    private DoubleLinkedList<Integer> list;

    @BeforeEach
    public void setUp() {
        list = new DoubleLinkedList<Integer>();
        list.addToEndOfList(1);
        list.addToEndOfList(3);
        list.addToEndOfList(9);
        list.addToEndOfList(15);
    }

    @Test
    @DisplayName("Should add 4 items at the end of the list and return list size and values in the correct order")
    public void shouldAdd4ItemsAtTheEndOfTheListAndReturnListSizeAndValuesInTheCorrectOrder() {

        assertEquals(4, list.getSize());
        assertArrayEquals(new Integer[]{1, 3, 9, 15},
                new Integer[]{
                        list.tryAdvance().get(),
                        list.tryAdvance().get(),
                        list.tryAdvance().get(),
                        list.tryAdvance().get()
                });
    }

    @Test
    @DisplayName("Should add 4 items to the beginning of the list and return list size and values in the correct order")
    public void shouldAdd4ItemsToTheBeginningOfTheListAndReturnListSizeAndValuesInTheCorrectOrder() {
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

    @Test
    @DisplayName("Should navigate right from end to start list")
    public void shouldNavigateRightFromEndToStartList() {
        assertArrayEquals(new int[]{15, 9, 3, 1},
                new int[]{
                        list.tryPrevious().get(),
                        list.tryPrevious().get(),
                        list.tryPrevious().get(),
                        list.tryPrevious().get()
                });
    }

    @Test
    @DisplayName("Should remove all items in the list from the beginning to the end of the list")
    public void shouldRemoveAllItemsInTheListFromTheBeginningToTheEndOfTheList() {
        Iterator<Integer> iterator = list.iterator();

        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }

        assertEquals(0, list.getSize());
    }

    @Test
    @DisplayName("Should remove all items in the list from the end to the beginning of the list")
    public void shouldRemoveAllItemsInTheListFromTheEndToTheBeginningOfTheList() {
        PreviousIterator<Integer> iterator = (PreviousIterator) list.iterator();

        while (iterator.hasPrevious()) {
            iterator.previous();
            iterator.remove();
        }

        assertEquals(0, list.getSize());
    }

    @Test
    @DisplayName("Should remove an item from middle list going forward")
    public void shouldRemoveAnItemFromMiddleListGoingForward() {
        Iterator<Integer> iterator = list.iterator();

        while (iterator.hasNext()) {

            if (iterator.next() == 9) {
                iterator.remove();
            }
        }

        assertIterableEquals(List.of(1, 3, 15), list.values());
        assertEquals(3, list.getSize());
    }

    @Test
    @DisplayName("Should remove am item from middle list going backwards")
    public void shouldRemoveAmItemFromMiddleListGoingBackwards() {
        PreviousIterator<Integer> iterator = (PreviousIterator<Integer>) list.iterator();

        while (iterator.hasPrevious()) {

            if (iterator.previous() == 9) {
                iterator.remove();
            }
        }

        assertIterableEquals(List.of(1, 3, 15), list.values());
        assertEquals(3, list.getSize());
    }

    @Test
    @DisplayName("Should return true when there is avalue in list")
    public void shouldReturnTrueWhenThereIsAValueInList() {
        assertTrue(list.contains(9));
    }

    @Test
    @DisplayName("Should return false when there is not avalue in list")
    public void shouldReturnFalseWhenThereIsNotAValueInList() {
        assertFalse(list.contains(0));
    }

    @Test
    @DisplayName("Should return the index when finding the value in list")
    public void shouldReturnTheIndexWhenFindingTheValueInList() {
        assertEquals(2, list.indexOf(9).get());
    }

    @Test
    @DisplayName("Should return optional empty when not finding value in list")
    public void shouldReturnOptionalEmptyWhenNotFindingValueInList() {
        assertTrue(list.indexOf(20).isEmpty());
    }

    @Test
    @DisplayName("Should throws illegal argument exception when index is less than zero")
    public void shouldThrowsIllegalArgumentExceptionWhenIndexIsLessThanZero() {
        assertThrows(IllegalArgumentException.class, () -> list.insertAt(-1, 10), "It's NOT possible insert element (10) at position -1");
    }

    @Test
    @DisplayName("Should throw illegal argument exception when index is bigger than list size")
    public void shouldThrowIllegalArgumentExceptionWhenIndexIsBiggerThanListSize() {
        assertThrows(IllegalArgumentException.class, () -> list.insertAt(5, 10), "It's NOT possible insert element (10) at position 5");
    }

    @Test
    @DisplayName("Should insert the value in index 2 of the list")
    public void shouldInsertTheValueInIndex2OfTheList() {
        assertTrue(list.insertAt(2, 500));
        assertEquals(list.getSize(), 5);
        assertIterableEquals(List.of(1, 3, 500, 9, 15), list.values());
    }

    @Test
    @DisplayName("Should insert the value at the beginning of the list")
    public void  shouldInsertTheValueAtTheBeginningOfTheList() {
        assertTrue(list.insertAt(0, 500));
        assertEquals(list.getSize(), 5);
        assertIterableEquals(List.of(500, 1, 3, 9, 15), list.values());
    }

    @Test
    @DisplayName("Should insert the value at list end")
    public void shouldInsertTheValueAtListEnd() {
        assertTrue(list.insertAt(list.getSize(), 500));
        assertEquals(list.getSize(), 5);
        assertIterableEquals(List.of(1, 3, 9, 15, 500), list.values());
    }
}
