package com.starikov.dash.service.impl;

import com.starikov.dash.entity.User;
import com.starikov.dash.repository.UserRepository;
import com.starikov.dash.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public List<User> getUsersByPosition(User.Position position) {
        return userRepository.findAllByPosition(position);
    }

    @Override
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }
}
