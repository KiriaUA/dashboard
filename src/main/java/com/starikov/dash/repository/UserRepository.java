package com.starikov.dash.repository;

import com.starikov.dash.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String name);
    User findByLogin(String login);
    List<User> findAllByPosition(User.Position position);
}
