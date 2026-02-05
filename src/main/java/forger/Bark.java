package forger;

/**
 * Custom exception class for Bond Forger chatbot error messages.
 * Used to display user-friendly error messages when invalid commands or inputs are provided.
 * 
 * @author Jordan
 */
public class Bark extends Exception {
    /**
     * Constructs a new Bark exception with the specified error message.
     * 
     * @param message The error message to display to the user
     */
    public Bark(String message) {
        super(message);
    }
}
