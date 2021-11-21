package de.hrw.verteiltesystemepraktikum.question;

public class QuestionNotFoundException extends RuntimeException{

    private String message;

    public QuestionNotFoundException(String message) {
        super(message);
        this.message = message;
    }

}

