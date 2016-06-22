package com.starikov.dash.service;

import com.starikov.dash.entity.User;

import java.util.List;

public interface IUserService {
    User getUserById(long id);
    User getUserByName(String name);
    User getUserByLogin(String login);
    List<User> getUsersByPosition(User.Position position);
    List<User> getUsers();
}
