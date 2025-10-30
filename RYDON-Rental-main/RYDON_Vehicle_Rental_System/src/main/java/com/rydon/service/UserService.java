package com.rydon.service;

import java.util.List;

import com.rydon.dao.UserDAO;
import com.rydon.model.User;

public class UserService {
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void registerUser(User user) {
        userDAO.addUser(user);
    }

    public User authenticate(String email, String password) {
        User user = userDAO.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    // ... (inside public class UserService)

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}
