package implementations;

import utilities.StackADT;
import utilities.Iterator;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;

/**
 * Custom Stack implementation using a raw array and fundamental data structure principles.
 *
 * @param <E> The type of elements held in this stack.
 */
public class MyStack<E> implements StackADT<E> {
    private E[] elements;     // Array to store stack elements
    private int size;         // Current number of elements in the stack
    private static final int INITIAL_CAPACITY = 10; // Default initial capacity of the stack

    // Constructor to initialize the stack with an initial capacity
    @SuppressWarnings("unchecked")
    public MyStack() {
        elements = (E[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public void push(E toAdd) throws NullPointerException {
        if (toAdd == null) throw new NullPointerException("Cannot add null element to stack");

        // Check if the array is full, and resize if necessary
        if (size == elements.length) {
            resize(); // Double the capacity of the array
        }
        
        elements[size++] = toAdd; // Add the element to the top and increment size
    }

    @Override
    public E pop() {
        if (isEmpty()) throw new EmptyStackException();

        E topElement = elements[--size]; // Retrieve and remove the top element
        elements[size] = null; // Clear the slot for garbage collection

        return topElement;
    }

    @Override
    public E peek() {
        if (isEmpty()) throw new EmptyStackException();
        return elements[size - 1]; // Return the top element without removing it
    }

    @Override
    public void clear() {
        // Clear all elements by setting each slot to null
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0; // Reset the size to 0
    }

    @Override
    public boolean isEmpty() {
        return size == 0; // The stack is empty if size is zero
    }

    @Override
    public int size() {
        return size; // Return the current number of elements in the stack
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) throw new NullPointerException("Cannot search for null element in stack");

        // Linear search for the element
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(toFind)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int search(E toFind) throws NullPointerException {
        if (toFind == null) throw new NullPointerException("Cannot search for null element in stack");

        // Search from top to bottom (LIFO order)
        for (int i = size - 1; i >= 0; i--) {
            if (elements[i].equals(toFind)) {
                return size - i; // Return 1-based position from the top
            }
        }
        return -1; // Element not found
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        
        // Copy elements in reverse order so the top of the stack is first
        for (int i = 0; i < size; i++) {
            array[i] = elements[size - 1 - i];
        }
        return array;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E[] toArray(E[] holder) throws NullPointerException {
        if (holder == null) throw new NullPointerException("Holder array cannot be null");

        if (holder.length < size) {
            holder = (E[]) new Object[size]; // Create a new array if holder is too small
        }

        // Copy elements in reverse order so the top of the stack is first
        for (int i = 0; i < size; i++) {
            holder[i] = elements[size - 1 - i];
        }

        // Set extra elements to null if holder is larger than stack
        if (holder.length > size) {
            holder[size] = null;
        }
        return holder;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = size - 1; // Start from the top of the stack

            @Override
            public boolean hasNext() {
                return index >= 0;
            }

            @Override
            public E next() {
                if (!hasNext()) throw new NoSuchElementException("No more elements in the iterator");
                return elements[index--]; // Return the current element and move down
            }
        };
    }

    @Override
    public boolean stackOverflow() {
        return false; // The stack can dynamically resize, so no overflow occurs
    }

    @Override
    public boolean equals(StackADT<E> that) {
        if (this.size() != that.size()) return false;

        Iterator<E> thisIterator = this.iterator();
        Iterator<E> thatIterator = that.iterator();

        while (thisIterator.hasNext() && thatIterator.hasNext()) {
            if (!thisIterator.next().equals(thatIterator.next())) {
                return false;
            }
        }
        return true;
    }

    // Helper method to resize the internal array when capacity is reached
    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = elements.length * 2; // Double the capacity
        E[] newArray = (E[]) new Object[newCapacity];
        
        // Copy elements to the new array
        for (int i = 0; i < size; i++) {
            newArray[i] = elements[i];
        }
        elements = newArray; // Replace old array with new larger array
    }
}
