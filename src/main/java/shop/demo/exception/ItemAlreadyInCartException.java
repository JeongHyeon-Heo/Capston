package shop.demo.exception;

public class ItemAlreadyInCartException extends RuntimeException {
    public ItemAlreadyInCartException(String message) {
        super(message);
    }
}