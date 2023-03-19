package com.springboot.springboot.dao;

import com.springboot.springboot.models.User;
import java.util.List;

public interface UserDao {

    List<User> getUser();

    void delete(Long id);

    void addUser(User user);

    User verifyCredentials(User user);

}
