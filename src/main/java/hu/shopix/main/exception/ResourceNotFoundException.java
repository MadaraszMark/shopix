package hu.shopix.main.exception;

public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " nem található ezzel az azonosítóval: " + id);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}


