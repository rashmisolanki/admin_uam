package org.myproject.uam.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class Response {
    private Long userId;
    private String pfNumber;
    private String name;
    private String emailId;
    private String userGroup;
    private LocalDate effectiveDate;
    private String level;
    private String reason;
    private String pendingFor;
    private String Status;
    private String rejectReason;
    private String createdBy;
    private String modifiedBy;
    private LocalDate modifiedDate;
    private LocalDate creationDate;
    private int isDeleted;
    private Long ofId;
}
