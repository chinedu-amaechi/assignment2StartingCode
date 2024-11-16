package utilities;
import java.io.*;
import java.util.EmptyStackException;

/**
 * The {@code StackADT} interface defines the essential operations for a generic stack data structure. 
 * Implementations should support stack operations like push, pop, and peek, with additional utility 
 * methods for iteration, comparison, and overflow detection.
 *
 * @author: Developed collaboratively by CPRG304: Team3.
 *
 * @param <E> the type of elements held in this stack
 */
public interface StackADT<E> extends Serializable {

    /**
     * Adds an element to the top of the stack.
     *
     * @param toAdd the element to push onto the stack
     * @throws NullPointerException if {@code toAdd} is null
     */
    public void push(E toAdd) throws NullPointerException;

    
    /**
     * Removes and returns the element at the top of the stack.
     *
     * @return the removed element
     * @throws EmptyStackException if the stack is empty
     */
    public E pop() throws EmptyStackException;

    
    /**
     * Retrieves the element at the top of the stack without removing it.
     *
     * @return the top element
     * @throws EmptyStackException if the stack is empty
     */
    public E peek() throws EmptyStackException;

    
    /**
     * Removes all elements from the stack.
     */
    public void clear();

    
    /**
     * Checks if the stack is empty.
     *
     * @return {@code true} if the stack has no elements
     */
    public boolean isEmpty();

    
    /**
     * Returns an array with all elements in the stack, in order from bottom to top.
     *
     * @return an array containing all elements in this stack
     */
    public Object[] toArray();

    
    /**
     * Populates the provided array with the stack's elements in order, 
     * or truncates to null if the array is larger.
     *
     * @param holder the array to store stack elements
     * @return the array with the stack's elements
     * @throws NullPointerException if {@code holder} is null
     */
    public E[] toArray(E[] holder) throws NullPointerException;

    
    /**
     * Checks if the stack contains a specified element.
     *
     * @param toFind the element to search for
     * @return {@code true} if the stack contains the element
     * @throws NullPointerException if {@code toFind} is null
     */
    public boolean contains(E toFind) throws NullPointerException;

    
    /**
     * Finds the 1-based position of an element in the stack from the top. 
     * Returns -1 if the element is not found.
     *
     * @param toFind the element to search for
     * @return the 1-based position from the top, or -1 if not found
     */
    public int search(E toFind);

    
    /**
     * Provides an iterator for the stack's elements in order.
     *
     * @return an iterator over the stack
     */
    public Iterator<E> iterator();

    
    /**
     * Checks if this stack is equal to another stack by comparing elements in order.
     *
     * @param that the stack to compare to
     * @return {@code true} if both stacks contain the same elements in the same order
     */
    public boolean equals(StackADT<E> that);

    
    /**
     * Returns the number of elements in the stack.
     *
     * @return the stack's element count
     */
    public int size();

    
    /**
     * Checks if the stack has reached its maximum capacity.
     *
     * @return {@code true} if the stack is full, otherwise {@code false}
     */
    public boolean stackOverflow();
}
