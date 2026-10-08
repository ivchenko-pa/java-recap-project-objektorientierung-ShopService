import java.util.NoSuchElementException;

public class ProductNotFoundException extends Exception {

    public ProductNotFoundException() {
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
