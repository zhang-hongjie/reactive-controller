package com.example.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "public", name = "datachangelog")
public class DataChangeLog {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "timestamp NOT NULL DEFAULT now()")
    private Date date;
    @Column
    private String actionType;
    @Column
    private String tableName;
    @Column
    private String rowId;
    @Column(columnDefinition = "json")
    private String jsonOld;
    @Column(columnDefinition = "json")
    private String jsonNew;

}
