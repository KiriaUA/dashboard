package com.starikov.dash.service;

public interface IJiraUserService {
    String getUserAvatar(String userLogin);
    int getOpenDefectsForUser(String userLogin);
}
