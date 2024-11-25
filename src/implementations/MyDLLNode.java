package implementations;

/**
 * Represents a node in a doubly linked list.
 *
 * Each node stores data and maintains references to both 
 * the previous and the next nodes in the list.
 *
 * @param <E> The type of data stored in the node.
 * @author Developed collaboratively by Team 3, CPRG304, Fall 2024.
 */
public class MyDLLNode<E> {
    E data;             // Data stored in this node
    MyDLLNode<E> prev;  // Reference to the previous node
    MyDLLNode<E> next;  // Reference to the next node

    
    
    /**
     * Constructs a new node with the specified data.
     * Initializes the previous and next references to null.
     *
     * @param data The data to store in this node.
     */
    public MyDLLNode(E data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}
