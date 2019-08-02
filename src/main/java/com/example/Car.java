package com.example;

import java.util.Date;

public class Car {

    private String id;
    private String comment;
    private Boolean isout;
    private Double estimation;
    private Date date;

    public Car setId(String id) {
        this.id = id;
        return this;
    }

    public Car setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Car setIsout(Boolean isout) {
        this.isout = isout;
        return this;
    }

    public Car setEstimation(Double estimation) {
        this.estimation = estimation;
        return this;
    }

    public Car setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public Boolean getIsout() {
        return isout;
    }

    public Double getEstimation() {
        return estimation;
    }

    public Date getDate() {
        return date;
    }
}
