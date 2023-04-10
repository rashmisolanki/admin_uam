package org.myproject.uam.entity;

import lombok.Data;

import javax.persistence.*;
@Data

@Entity
@Table
public class UamUserGroupId {
    @Id
    @Column
    @SequenceGenerator(name="uamUserSequenceGen",sequenceName = "uam_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "uamUserSequenceGen")
    private String pfNumber;
    @Column
    private String groupId;

}
