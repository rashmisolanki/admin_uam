package org.myproject.uam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Response {
    private Long userId;
    private String pfNumber;
    private String name;
    private String emailId;
    private LocalDate effectiveDate;
    private String reason;
    private String pendingFor;
    private String groupID;
    private String status;
    private String rejectReason;
    private String createdBy;
    private String modifiedBy;
    private LocalDate modifiedDate;
    private LocalDate creationDate;
    private int isDeleted;
    private Long ofId;
}
