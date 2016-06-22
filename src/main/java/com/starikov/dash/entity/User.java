package com.starikov.dash.entity;

import javax.persistence.*;

@Entity
public class User {

    public enum Position {
        DEV, QA, BA, DESIGN
    }

    @Id
    @GeneratedValue
    private String id;

    private String name;

    private String login;

    @Enumerated(EnumType.STRING)
    private Position position;

    public User() {
    }

    public User(String name, String login, Position position) {
        this.name = name;
        this.login = login;
        this.position = position;
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

    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
}
