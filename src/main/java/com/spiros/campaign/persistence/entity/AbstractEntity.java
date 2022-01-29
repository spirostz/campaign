package com.spiros.campaign.persistence.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue
    private Long id;

    //TODO: auto generated create and update date

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
