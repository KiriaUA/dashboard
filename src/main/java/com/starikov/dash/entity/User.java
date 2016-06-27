package com.starikov.dash.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "USER_DETAILS")
public class User {

    public enum Position {
        DEV, QA, BA, DESIGN
    }

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String login;

    @Enumerated(EnumType.STRING)
    private Position position;

    @JsonIgnore
    @Embedded
    private UserJiraDetails userDetails;

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

    public Long getId() {
        return id;
    }
    public void setId(Long ID) {
        this.id = ID;
    }

    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }

    public UserJiraDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserJiraDetails userDetails) {
        this.userDetails = userDetails;
    }
}
