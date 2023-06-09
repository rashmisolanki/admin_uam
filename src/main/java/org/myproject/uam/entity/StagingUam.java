package org.myproject.uam.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="stagingUam")
public class StagingUam {
    @Id
    @Column
    @SequenceGenerator(name = "uamUserSequenceGen", sequenceName = "uam_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uamUserSequenceGen")
    private long requestId;

    @Column
    private String actionOnApproval;
    @Column
    private String status;
    @Column
    private long isDeleted;
    @Column
    private long toId;
    @Column
    private String groupId;
    @JsonIgnoreProperties("stagingUam")
    @OneToOne
    @JoinColumn(name="fromId", referencedColumnName = "userId")
    private UamUser uamUser;
}

