package de.hrw.verteiltesystemepraktikum.orders;

public class OrderNotFoundException extends RuntimeException {

    private String message;

    public OrderNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
