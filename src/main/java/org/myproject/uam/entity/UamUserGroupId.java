package org.myproject.uam.entity;

import lombok.Data;

import javax.persistence.*;
@Data

@Entity
@Table
public class UamUserGroupId {
    @Id
    @Column
    private String pfNumber;
    @Column
    private String groupId;

}
