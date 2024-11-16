package utilities;

import java.util.*;
import java.io.*;

/**
 * The {@code QueueADT} interface defines the core operations for a generic queue data structure, 
 * supporting enqueue, dequeue, and peek operations, as well as utility methods for searching, 
 * iteration, and fullness checks.
 *
 * @param <E> the type of elements held in this queue
 * 
 * @author: Developed collaboratively by CPRG340: Team3.
 */
public interface QueueADT<E> extends Serializable {

    
    /**
     * The {@code enqueue} method adds an element to the end of the queue.
     *
     * @param toAdd the element to enqueue
     * @throws NullPointerException if {@code toAdd} is null
     */
    public void enqueue(E toAdd) throws NullPointerException;

    
    /**
     * The {@code dequeue} method removes and returns the element at the front of the queue.
     *
     * @return the element removed from the front of the queue
     * @throws EmptyQueueException if the queue is empty
     */
    public E dequeue() throws EmptyQueueException;

    
    /**
     * The {@code peek} method retrieves the element at the front of the queue without removing it.
     *
     * @return the front element
     * @throws EmptyQueueException if the queue is empty
     */
    public E peek() throws EmptyQueueException;

    
    /**
     * This method, {@code dequeueAll}, removes all elements from the queue, leaving it empty.
     */
    public void dequeueAll();

    
    /**
     * The {@code isEmpty} method checks if the queue is empty.
     *
     * @return {@code true} if the queue has no elements
     */
    public boolean isEmpty();

    
    /**
     * The {@code contains} method checks if the queue contains a specified element.
     *
     * @param toFind the element to search for
     * @return {@code true} if the queue contains the specified element
     * @throws NullPointerException if {@code toFind} is null
     */
    public boolean contains(E toFind) throws NullPointerException;

    
    /**
     * This method, {@code search}, returns the 1-based position of an element in the queue from the front.
     * Returns -1 if the element is not in the queue.
     *
     * @param toFind the element to search for
     * @return the 1-based position from the front, or -1 if not found
     */
    public int search(E toFind);

    
    /**
     * The {@code iterator} method provides an iterator over the elements in the queue in proper sequence.
     *
     * @return an iterator over the queue's elements
     */
    public Iterator<E> iterator();

    
    /**
     * The {@code equals} method compares this queue with another queue for equality by examining elements in order.
     *
     * @param that the queue to compare with
     * @return {@code true} if both queues contain the same elements in the same order
     */
    public boolean equals(QueueADT<E> that);

    
    /**
     * The {@code toArray} method returns an array containing all elements in the queue, in order from front to back.
     *
     * @return an array of the queue's elements
     */
    public Object[] toArray();

    
    /**
     * The {@code toArray} method fills the provided array with the queue's elements in order, 
     * or sets unused elements to {@code null} if the array is larger.
     *
     * @param holder the array to store the queue's elements
     * @return the array containing the queue's elements
     * @throws NullPointerException if {@code holder} is null
     */
    public E[] toArray(E[] holder) throws NullPointerException;

    
    /**
     * The {@code isFull} method checks if the queue has reached its maximum capacity.
     *
     * @return {@code true} if the queue is full, otherwise {@code false}
     */
    public boolean isFull();

    
    /**
     * The {@code size} method returns the number of elements in the queue.
     *
     * @return the number of elements in the queue
     */
    public int size();
}
