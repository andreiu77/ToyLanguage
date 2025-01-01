package exceptions;

public class EmptyStatesList extends RuntimeException {
    public EmptyStatesList(String message) {
        super(message);
    }
}
