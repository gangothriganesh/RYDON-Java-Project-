package com.rydon.dao;

import com.rydon.model.User;
import java.util.List;

public interface UserDAO {
    void addUser(User user);
    User getUserByEmail(String email);
    List<User> getAllUsers();
}
