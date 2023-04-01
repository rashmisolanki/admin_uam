package org.myproject.uam.exception;

public class UserAlreadyPresentException extends RuntimeException {
    public UserAlreadyPresentException(String message)
    {
        super(message);
    }

}
