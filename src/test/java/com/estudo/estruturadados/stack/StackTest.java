package com.estudo.estruturadados.stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StackTest {

    private Stack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.push(40);
        stack.push(50);
    }

    @Test
    @DisplayName("Should add all values in correct order")
    public void shouldAddAllValuesInCorrectOrder() {
        Iterator<Integer> iterator = stack.iterator();
        List<Integer> list = new ArrayList<>();

        while(iterator.hasNext()) {
            list.add(iterator.next());
        }

        assertIterableEquals(List.of(50,40,30,20,10), list);
        assertEquals(stack.size(), 5);
    }

    @Test
    @DisplayName("Should return true when there is not elements")
    public void shouldReturnTrueWhenThereIsNotElements() {
        Iterator<Integer> iterator = stack.iterator();

        while(iterator.hasNext()) {
            iterator.remove();
        }

        assertEquals(stack.size(), 0);
        assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("Should return true when contains an value")
    public void shouldReturnTrueWhenContainsAnValue() {
        assertTrue(stack.contains(20));
    }

    @Test
    @DisplayName("Should return false when contains an value")
    public void shouldReturnFalseWhenContainsAnValue() {
        assertFalse(stack.contains(-20));
    }

    @Test
    @DisplayName("Should get the value but it still remains in the list")
    public void shouldGetTheValueButItStillRemainsInTheList() {
        assertEquals(50, stack.peek().orElseThrow());
        assertEquals(stack.size(), 5);
        assertIterableEquals(List.of(50,40,30,20,10), stack.toList());
    }

    @Test
    @DisplayName("Should get all values using forEachRemaining")
    public void shouldGetAllValuesUsingForEachRemaining() {
        List<Integer> list = new ArrayList<>();
        stack.iterator().forEachRemaining(list::add);

        assertEquals(list.size(), 5);
        assertIterableEquals(List.of(50,40,30,20,10), list);
    }
}