package implementations;

import utilities.ListADT;
import utilities.Iterator;
import java.util.NoSuchElementException;

/**
 * A dynamic, resizable array-based implementation of the ListADT interface.
 *
 * This class provides functionalities similar to Java's ArrayList. It allows 
 * storage, manipulation, and iteration over elements of type E. The internal 
 * array automatically resizes to accommodate more elements when required.
 *
 * Features include:
 * - Adding elements at specified or default positions.
 * - Removing elements by index or value.
 * - Efficient access to elements using an index.
 * - Dynamic resizing to optimize memory usage.
 *
 * Operations requiring valid indices, such as get, set, or remove, will throw 
 * an IndexOutOfBoundsException if the index is out of range. Null elements 
 * are not allowed and will result in a NullPointerException.
 *
 * @param <E> The type of elements stored in this list.
 * @author Team3: Assignment 2 for CPRG304
 */
public class MyArrayList<E> implements ListADT<E> {
    private E[] elements; // Array to store elements
    private int size; // Number of elements in the list

    /**
     * Constructs an empty list with an initial capacity of 10.
     */
    public MyArrayList() {
        elements = (E[]) new Object[10];
        size = 0;
    }

    /**
     * Ensures that the internal array has enough capacity for additional elements.
     * If the array is full, its size is doubled.
     */
    private void ensureCapacity() {
        if (size >= elements.length) {
            E[] newElements = (E[]) new Object[elements.length * 2];
            for (int i = 0; i < size; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        elements = (E[]) new Object[10];
        size = 0;
    }

    @Override
    public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
        if (toAdd == null) {
            throw new NullPointerException("Element to add cannot be null.");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of range.");
        }
        ensureCapacity();
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }
        elements[index] = toAdd;
        size++;
        return true;
    }

    @Override
    public boolean add(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Element to add cannot be null.");
        }
        ensureCapacity();
        elements[size++] = toAdd;
        return true;
    }

    @Override
    public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Collection to add cannot be null.");
        }
        for (int i = 0; i < toAdd.size(); i++) {
            add(toAdd.get(i));
        }
        return true;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range.");
        }
        return elements[index];
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range.");
        }
        E removedElement = elements[index];
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[--size] = null;
        return removedElement;
    }

    @Override
    public E remove(E toRemove) throws NullPointerException {
        if (toRemove == null) {
            throw new NullPointerException("Element to remove cannot be null.");
        }
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(toRemove)) {
                return remove(i);
            }
        }
        return null;
    }

    @Override
    public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
        if (toChange == null) {
            throw new NullPointerException("Element to set cannot be null.");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range.");
        }
        E oldValue = elements[index];
        elements[index] = toChange;
        return oldValue;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) {
            throw new NullPointerException("Element to find cannot be null.");
        }
        for (int i = 0; i < size; i++) {
            if (toFind.equals(elements[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public E[] toArray(E[] toHold) throws NullPointerException {
        if (toHold == null) {
            throw new NullPointerException("Array to hold elements cannot be null.");
        }
        if (toHold.length < size) {
            E[] newArray = (E[]) new Object[size];
            for (int i = 0; i < size; i++) {
                newArray[i] = elements[i];
            }
            return newArray;
        }
        for (int i = 0; i < size; i++) {
            toHold[i] = elements[i];
        }
        if (toHold.length > size) {
            toHold[size] = null;
        }
        return toHold;
    }

    @Override
    public Object[] toArray() {
        E[] newArray = (E[]) new Object[size];
        for (int i = 0; i < size; i++) {
            newArray[i] = elements[i];
        }
        return newArray;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public E next() throws NoSuchElementException {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements to iterate.");
                }
                return elements[cursor++];
            }
        };
    }
}
