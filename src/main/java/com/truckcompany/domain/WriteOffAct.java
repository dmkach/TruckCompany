package com.truckcompany.domain;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Table(name = "write_off_act")
public class WriteOffAct {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column (name = "count")
    private Integer count;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }
    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
}