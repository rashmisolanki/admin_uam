package org.myproject.uam.exception;

public class NoRequestFoundException extends RuntimeException
{
    public NoRequestFoundException(String message)
    {
        super(message);
    }
}
