package implementations;

import utilities.QueueADT;
import utilities.EmptyQueueException;
import java.util.NoSuchElementException;
import utilities.Iterator;

/**
 * A Queue implementation using a custom doubly linked list (DLL).
 *
 * @param <E> the type of elements held in this queue
 */
public class MyQueue<E> implements QueueADT<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    // Inner class for doubly linked list node
    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev;

        Node(E data) {
            this.data = data;
        }
    }

    public MyQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public void enqueue(E toAdd) throws NullPointerException {
        if (toAdd == null) throw new NullPointerException("Cannot add null element to queue");

        Node<E> newNode = new Node<>(toAdd);
        if (tail == null) {
            head = newNode; // If the queue is empty, head and tail point to the new node
        } else {
            tail.next = newNode; // Link the new node at the end
            newNode.prev = tail;
        }
        tail = newNode; // Update the tail to the new node
        size++;
    }

    @Override
    public E dequeue() throws EmptyQueueException {
        if (isEmpty()) throw new EmptyQueueException("Queue is empty");

        E frontData = head.data; // Get the data from the front (head)
        head = head.next; // Move head to the next node
        if (head != null) {
            head.prev = null; // Detach the old head
        } else {
            tail = null; // Queue is now empty
        }
        size--;
        return frontData;
    }

    @Override
    public E peek() throws EmptyQueueException {
        if (isEmpty()) throw new EmptyQueueException("Queue is empty");
        return head.data; // Return the data at the front (head) without removing it
    }

    @Override
    public void dequeueAll() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) throw new NullPointerException("Cannot search for null element in queue");

        Node<E> current = head;
        while (current != null) {
            if (current.data.equals(toFind)) return true;
            current = current.next;
        }
        return false;
    }

    @Override
    public int search(E toFind) {
        if (toFind == null) throw new NullPointerException("Cannot search for null element in queue");

        Node<E> current = head;
        int position = 1; // 1-based index
        while (current != null) {
            if (current.data.equals(toFind)) return position;
            current = current.next;
            position++;
        }
        return -1;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        Node<E> current = head;
        int index = 0;
        while (current != null) {
            array[index++] = current.data;
            current = current.next;
        }
        return array;
    }

    @Override
    public E[] toArray(E[] holder) throws NullPointerException {
        if (holder == null) throw new NullPointerException("Holder array cannot be null");

        if (holder.length < size) {
            holder = (E[]) new Object[size];
        }
        
        Node<E> current = head;
        int index = 0;
        while (current != null) {
            holder[index++] = current.data;
            current = current.next;
        }

        if (holder.length > size) {
            holder[size] = null; // Set the element after the last queue element to null
        }
        return holder;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head;

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
        };
    }

    @Override
    public boolean isFull() {
        return false; // This queue dynamically expands as needed
    }

    @Override
    public boolean equals(QueueADT<E> that) {
        if (this.size() != that.size()) return false;
        
        Iterator<E> thisIterator = this.iterator();
        Iterator<E> thatIterator = that.iterator();

        while (thisIterator.hasNext() && thatIterator.hasNext()) {
            if (!thisIterator.next().equals(thatIterator.next())) return false;
        }
        return true;
    }
}
