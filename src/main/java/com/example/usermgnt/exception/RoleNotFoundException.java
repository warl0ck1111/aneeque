package com.example.usermgnt.exception;

public class RoleNotFoundException extends RuntimeException{
    /* Serial version */
    private static final long serialVersionUID = 5434016005679159613L;

    /**
     * Default constructor, no message put in exception.
     */
    public RoleNotFoundException() {
        super();
    }

    /**
     * Constructor with given message put in exception.
     *
     * @param message the detail message.
     */
    public RoleNotFoundException(String message) {
        super(message);
    }
}
