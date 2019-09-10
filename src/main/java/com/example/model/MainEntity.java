package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "public", name = "main")
@Audited(withModifiedFlag = true)
@EntityListeners(AuditingEntityListener.class)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "VARCHAR(50)")
    private String id;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(columnDefinition = "BOOLEAN")
    private Boolean isout;

    @Column(columnDefinition = "NUMERIC")
    private Double estimation;

    @Column
    private Date date;
}