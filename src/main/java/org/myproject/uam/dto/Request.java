package org.myproject.uam.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Request {
    private String pfNumber;
    private String name;
    private String emailId;
    private String userGroup;
    private LocalDate effectiveDate;
    private String userGroupId;
    private  String level;
    private String reason;


    public Request(int i, String rashmi, String s, String invesment, LocalDate localDate, String level3, String l3, String new_joiner) {
    }

    public Request() {

    }
}
