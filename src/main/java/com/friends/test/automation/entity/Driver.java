package com.friends.test.automation.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Driver extends TanistanBaseEntity<String> {

    private String address;
    private Integer port;

    @ManyToOne
    private UserEntity userEntity;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}