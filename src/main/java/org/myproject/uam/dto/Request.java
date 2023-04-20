package org.myproject.uam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    private String pfNumber;
    private String createdBy;
    private String modifiedBy;
    private LocalDate modifiedDate;
    private String name;
    private String emailId;
    private String groupId;
    private LocalDate effectiveDate;
    private String reason;
    private String actionOnApproval;
    private String status;
    private String pendingFor;
    private Long userId;

}
