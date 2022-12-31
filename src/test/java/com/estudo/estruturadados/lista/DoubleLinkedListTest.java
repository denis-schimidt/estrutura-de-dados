package com.estudo.estruturadados.lista;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

public class DoubleLinkedListTest {

    private DoubleLinkedList<Integer> list;

    @BeforeEach
    public void setUp() {
        list = new DoubleLinkedList<>();
        list.insertAtEnd(1);
        list.insertAtEnd(3);
        list.insertAtEnd(9);
        list.insertAtEnd(15);
    }

    @Test
    @DisplayName("Should add 4 items at the end of the list and return list size and values in the correct order")
    public void shouldAdd4ItemsAtTheEndOfTheListAndReturnListSizeAndValuesInTheCorrectOrder() {

        Assertions.assertEquals(4, list.getSize());
        Assertions.assertArrayEquals(new Integer[]{1, 3, 9, 15},
                new Integer[]{
                        list.tryAdvance().orElseThrow(),
                        list.tryAdvance().orElseThrow(),
                        list.tryAdvance().orElseThrow(),
                        list.tryAdvance().orElseThrow()
                });
    }

    @Test
    @DisplayName("Should add 4 items to the beginning of the list and return list size and values in the correct order")
    public void shouldAdd4ItemsToTheBeginningOfTheListAndReturnListSizeAndValuesInTheCorrectOrder() {
        DoubleLinkedList<Integer> list = new DoubleLinkedList<>();
        list.insertAtBeginning(1);
        list.insertAtBeginning(3);
        list.insertAtBeginning(9);
        list.insertAtBeginning(15);

        Assertions.assertEquals(4, list.getSize());
        Assertions.assertArrayEquals(new Integer[]{15, 9, 3, 1},
                new Integer[]{
                        list.tryAdvance().orElseThrow(),
                        list.tryAdvance().orElseThrow(),
                        list.tryAdvance().orElseThrow(),
                        list.tryAdvance().orElseThrow()
                });
    }

    @Test
    @DisplayName("Should navigate right from end to start list")
    public void shouldNavigateRightFromEndToStartList() {
        Assertions.assertArrayEquals(new int[]{15, 9, 3, 1},
                new int[]{
                        list.tryPrevious().orElseThrow(),
                        list.tryPrevious().orElseThrow(),
                        list.tryPrevious().orElseThrow(),
                        list.tryPrevious().orElseThrow()
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

        Assertions.assertEquals(0, list.getSize());
    }

    @SuppressWarnings("rawtypes")
    @Test
    @DisplayName("Should remove all items in the list from the end to the beginning of the list")
    public void shouldRemoveAllItemsInTheListFromTheEndToTheBeginningOfTheList() {
        var iterator = (PreviousIterator) list.iterator();

        while (iterator.hasPrevious()) {
            iterator.previous();
            iterator.remove();
        }

        Assertions.assertEquals(0, list.getSize());
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

        Assertions.assertIterableEquals(List.of(1, 3, 15), list.values());
        Assertions.assertEquals(3, list.getSize());
    }

    @Test
    @DisplayName("Should remove am item from middle list going backwards")
    public void shouldRemoveAmItemFromMiddleListGoingBackwards() {
        var iterator = (PreviousIterator<Integer>) list.iterator();

        while (iterator.hasPrevious()) {

            if (iterator.previous() == 9) {
                iterator.remove();
            }
        }

        Assertions.assertIterableEquals(List.of(1, 3, 15), list.values());
        Assertions.assertEquals(3, list.getSize());
    }

    @Test
    @DisplayName("Should return true when there is value in list")
    public void shouldReturnTrueWhenThereIsAValueInList() {
        Assertions.assertTrue(list.contains(9));
    }

    @Test
    @DisplayName("Should return false when there is not value in list")
    public void shouldReturnFalseWhenThereIsNotAValueInList() {
        Assertions.assertFalse(list.contains(0));
    }

    @Test
    @DisplayName("Should return the index when finding the value in list")
    public void shouldReturnTheIndexWhenFindingTheValueInList() {
        Assertions.assertEquals(2, list.indexOf(9).orElseThrow());
    }

    @Test
    @DisplayName("Should return optional empty when not finding value in list")
    public void shouldReturnOptionalEmptyWhenNotFindingValueInList() {
        Assertions.assertTrue(list.indexOf(20).isEmpty());
    }

    @Test
    @DisplayName("Should throws illegal argument exception when index is less than zero")
    public void shouldThrowsIllegalArgumentExceptionWhenIndexIsLessThanZero() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> list.insertAt(-1, 10), "It's NOT possible insert element (10) at position -1");
    }

    @Test
    @DisplayName("Should throw illegal argument exception when index is bigger than list size")
    public void shouldThrowIllegalArgumentExceptionWhenIndexIsBiggerThanListSize() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> list.insertAt(5, 10), "It's NOT possible insert element (10) at position 5");
    }

    @Test
    @DisplayName("Should insert the value in index 2 of the list")
    public void shouldInsertTheValueInIndex2OfTheList() {
        Assertions.assertTrue(list.insertAt(2, 500));
        Assertions.assertEquals(list.getSize(), 5);
        Assertions.assertIterableEquals(List.of(1, 3, 500, 9, 15), list.values());
    }

    @Test
    @DisplayName("Should insert the value at the beginning of the list")
    public void  shouldInsertTheValueAtTheBeginningOfTheList() {
        Assertions.assertTrue(list.insertAt(0, 500));
        Assertions.assertEquals(list.getSize(), 5);
        Assertions.assertIterableEquals(List.of(500, 1, 3, 9, 15), list.values());
    }

    @Test
    @DisplayName("Should insert the value at list end")
    public void shouldInsertTheValueAtListEnd() {
        Assertions.assertTrue(list.insertAt(list.getSize(), 500));
        Assertions.assertEquals(list.getSize(), 5);
        Assertions.assertIterableEquals(List.of(1, 3, 9, 15, 500), list.values());
    }
}
