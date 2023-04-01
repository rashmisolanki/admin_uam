package org.myproject.uam.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table
@Data
public class Admin {

    @Id
    @Column
    @SequenceGenerator(name="userSequenceGen",sequenceName = "abc_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "userSequenceGen")
     private Long requestId;
    @Column
    private String pfNumber;
    @Column
    private String name;
    @Column
    private String emailId;
    @Column
    private LocalDate effectivedate;
    @Column
    private String usergroup;
    @Column
    private String usergroupId;
    @Column
    private String level;
    @Column
    private String reason;
    @Column
    private String pendingStatus;
    @Column
    private  String status;

}
