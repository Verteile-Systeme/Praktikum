package de.hrw.verteiltesystemepraktikum.appuser;

public class MailAlreadyExistsException extends RuntimeException{

    private String message;

    public MailAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }

    public MailAlreadyExistsException() {

    }
}
