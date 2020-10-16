package com.authine.cloudpivot.mybatis.entity;

import java.math.BigDecimal;
import java.util.Date;

public class RLEntity {
    private String id;
    private Date startdate1;
    private Date enddate1;
    private BigDecimal freedate1;
    private BigDecimal workdate1;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartdate1() {
        return startdate1;
    }

    public void setStartdate1(Date startdate1) {
        this.startdate1 = startdate1;
    }

    public Date getEnddate1() {
        return enddate1;
    }

    public void setEnddate1(Date enddate1) {
        this.enddate1 = enddate1;
    }

    public BigDecimal getFreedate1() {
        return freedate1;
    }

    public void setFreedate1(BigDecimal freedate1) {
        this.freedate1 = freedate1;
    }

    public BigDecimal getWorkdate1() {
        return workdate1;
    }

    public void setWorkdate1(BigDecimal workdate1) {
        this.workdate1 = workdate1;
    }

    @Override
    public String toString() {
        return "RLEntity{" +
                "id=" + id +
                ", startdate1=" + startdate1 +
                ", enddate1=" + enddate1 +
                ", freedate1=" + freedate1 +
                ", workdate1=" + workdate1 +
                '}';
    }
}
