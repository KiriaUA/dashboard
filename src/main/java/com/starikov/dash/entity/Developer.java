package com.starikov.dash.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Developer {

    @Id
    @GeneratedValue
    private String id;

    private String name;

    private String login;

    public Developer() {
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getId() {
        return id;
    }
    public void setId(String ID) {
        this.id = ID;
    }
}
