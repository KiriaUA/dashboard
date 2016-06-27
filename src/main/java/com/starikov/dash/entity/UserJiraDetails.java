package com.starikov.dash.entity;

import javax.persistence.Embeddable;

@Embeddable
public class UserJiraDetails {

    private String avatarPath;
    private int openDefects;

    public UserJiraDetails(String avatarPath, int openDefects) {
        this.avatarPath = avatarPath;
        this.openDefects = openDefects;
    }

    public UserJiraDetails() {
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public int getOpenDefects() {
        return openDefects;
    }

    public void setOpenDefects(int openDefects) {
        this.openDefects = openDefects;
    }
}
