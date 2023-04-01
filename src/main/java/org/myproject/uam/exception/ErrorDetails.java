package org.myproject.uam.exception;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String description;
    private List<String> errors;

    public ErrorDetails(Date timestamp, String message, String description) {
        this.timestamp=timestamp;
        this.message=message;
        this.description=description;

    }
}
