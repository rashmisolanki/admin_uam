package org.myproject.uam.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="uamUser")

public class UamUser {
    @Id
    @Column
    @SequenceGenerator(name="uamUserSequenceGen",sequenceName = "uam_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "uamUserSequenceGen")
    private Long userId;
    @Column
    private String pfNumber;
    @Column
    private String name;
    @Column
    private String status;
    @Column
    private  String pendingFor;
    @Column
    private String createdBy;
    @Column
    private LocalDate creationDate;
    @Column
    private String modifiedBy;
    @Column
    private LocalDate modifiedDate;
    @Column
    private String emailId;
    @Column
    private LocalDate effectiveDate;
    @Column
    private String reason;
    @Column
    private int isDeleted;
    @Column
    private long ofId;
    @JsonIgnoreProperties("uamUser")
    @OneToOne(mappedBy = "uamUser",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private StagingUam stagingUam;

}
