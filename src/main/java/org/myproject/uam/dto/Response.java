package org.myproject.uam.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class Response {
    private Long requestId;
    private String pfNumber;
    private String name;
    private String emailId;
    private String userGroup;
    private LocalDate effectiveDate;
    private String userGroupId;
    private String level;
    private String reason;
    private String pendingStatus;
    private String Status;
    private String rejectReason;
}
