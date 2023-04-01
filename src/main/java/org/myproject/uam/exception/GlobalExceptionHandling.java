package org.myproject.uam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandling {
    @ExceptionHandler(PfNumberNotFound.class)
            public ResponseEntity<ErrorDetails> PfNumberNotFound(PfNumberNotFound ex, WebRequest webRequest)
    {
       ErrorDetails errorDetails=new ErrorDetails(new Date(), ex.getMessage(),webRequest.getDescription(false));
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }
    @ExceptionHandler(UserAlreadyPresentException.class)
            public ResponseEntity<ErrorDetails> UserAlreadyPresentException(UserAlreadyPresentException ex, WebRequest webRequest)
    {
      ErrorDetails errorDetails= new ErrorDetails(new Date(), ex.getMessage(), webRequest.getDescription(false)) ;
      return ResponseEntity.status(HttpStatus.OK).body(errorDetails);
    }

    @ExceptionHandler(NoRequestFoundException.class)
    public ResponseEntity<ErrorDetails> NoRequestFoundException(NoRequestFoundException ex, WebRequest webRequest)
    {
        ErrorDetails errorDetails= new ErrorDetails(new Date(), ex.getMessage(), webRequest.getDescription(false)) ;
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(EffectiveStartDateException.class)
    public ResponseEntity<ErrorDetails> EffectiveStartDateException(EffectiveStartDateException ex, WebRequest webRequest)
    {
        ErrorDetails errorDetails= new ErrorDetails(new Date(), ex.getMessage(), webRequest.getDescription(false)) ;
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(NoUserIdChangeException.class)
    public ResponseEntity<ErrorDetails> NoUserIdChangeException(NoUserIdChangeException ex, WebRequest webRequest)
    {
        ErrorDetails errorDetails= new ErrorDetails(new Date(), ex.getMessage(), webRequest.getDescription(false)) ;
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }
}

