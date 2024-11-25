package utilities;

/**
 * Exception thrown when an operation is performed on an empty queue.
 * 
 * @author Developed collaboratively by Team 3, CPRG304, Fall 2024.
 * 
 */
public class EmptyQueueException extends RuntimeException {
    
    
    /**
     * Constructs a new EmptyQueueException with {@code null} as its detail message.
     */
    public EmptyQueueException() {
        super("Queue is empty");
    }

    
    /**
     * Constructs a new EmptyQueueException with a specified detail message.
     *
     * @param message the detail message
     */
    public EmptyQueueException(String message) {
        super(message);
    }
    
}
