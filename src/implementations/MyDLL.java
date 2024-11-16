package implementations;

import utilities.ListADT;
import utilities.Iterator;

import java.util.NoSuchElementException;

/**
 *
 * @author chyme
 */
public class MyDLL<E> implements ListADT<E> {
    private MyDLLNode<E> head;
    private MyDLLNode<E> tail;
    private int size;

    
    public MyDLL() {
        head = null;
        tail = null;
        size = 0;
    }

    
    @Override
    public int size() {
        return size;
    }

    
    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    
    @Override
    public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
        if (toAdd == null) throw new NullPointerException();
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        MyDLLNode<E> newNode = new MyDLLNode<>(toAdd);

        if (index == 0) { // Insert at head
            newNode.next = head;
            if (head != null) head.prev = newNode;
            head = newNode;
            if (size == 0) tail = newNode;
        } else if (index == size) { // Insert at tail
            newNode.prev = tail;
            if (tail != null) tail.next = newNode;
            tail = newNode;
        } else { // Insert at middle
            MyDLLNode<E> current = getNode(index);
            newNode.next = current;
            newNode.prev = current.prev;
            if (current.prev != null) current.prev.next = newNode;
            current.prev = newNode;
        }

        size++;
        return true;
    }

    
    @Override
    public boolean add(E toAdd) throws NullPointerException {
        return add(size, toAdd);
    }

        
    @Override
    public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("The collection to add cannot be null.");
        }
        for (int i = 0; i < toAdd.size(); i++){
            add(toAdd.get(i));
        }   
    return true;
}

    

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        return getNode(index).data;
    }

    
    
    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        MyDLLNode<E> nodeToRemove = getNode(index);

        if (nodeToRemove.prev != null) nodeToRemove.prev.next = nodeToRemove.next;
        else head = nodeToRemove.next;

        if (nodeToRemove.next != null) nodeToRemove.next.prev = nodeToRemove.prev;
        else tail = nodeToRemove.prev;

        size--;
        return nodeToRemove.data;
    }

    
    
    @Override
    public E remove(E toRemove) throws NullPointerException {
        if ( toRemove == null ) {
            throw new NullPointerException("The element to Remove is null");
        }
        
        MyDLLNode<E> current = head;
        while (current != null) {
            if (current.data.equals(toRemove)) {
                if (current.prev != null) current.prev.next = current.next;
                else head = current.next;
                if (current.next != null) current.next.prev = current.prev;
                else tail = current.prev;

                size--;
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    
    
    @Override
    public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
        if ( toChange == null ) {
            throw new NullPointerException("The element to Set is null");
        }
        MyDLLNode<E> node = getNode(index);
        E oldValue = node.data;
        node.data = toChange;
        return oldValue;
    }

    
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    
    
    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if ( toFind == null ) {
            throw new NullPointerException("The element to Find is null");
        }
        MyDLLNode<E> current = head;
        while (current != null) {
            if (current.data.equals(toFind)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    
    
    
    @Override
    public E[] toArray(E[] toHold) throws NullPointerException {
        if (toHold == null) {
            throw new NullPointerException ("The array to hold elements is null");
        }
        if (toHold.length < size) {
            
            // Create a new array of the same type as toHold using iteration
            E[] newArray = (E[]) new Object[size];
            for (int i = 0; i < toHold.length; i++) {
                newArray[i] = toHold[i];
            }
            toHold = newArray;
        }
        int index = 0;
        for (MyDLLNode<E> current = head; current != null; current = current.next){
            toHold[index++] = current.data;
        }
        if (toHold.length > size) {
            toHold[size] = null;
        }
        return toHold;
    }

    
    
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int index = 0;
        for (MyDLLNode<E> current = head; current != null; current = current.next) {
            array[index++] = current.data;
        }
        return array;
    }

    
    
    @Override
    public Iterator<E> iterator() {
        return new MyDLLIterator();
    }

    
    
    private MyDLLNode<E> getNode(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        MyDLLNode<E> current = head;
        for (int i = 0; i < index; i++) current = current.next;
        return current;
    }

    
    
    private class MyDLLIterator implements Iterator<E> {
        private MyDLLNode<E> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        
        
        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            E data = current.data;
            current = current.next;
            return data;
        }
    }
}
