package de.hrw.verteiltesystemepraktikum.order;

public class OrderNotFoundException extends RuntimeException {

    private String message;

    public OrderNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
