package com.rydon.controller;

import com.rydon.model.User;
import com.rydon.service.UserService;

public class LoginController {
    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    public User login(String email, String password) {
        return userService.authenticate(email, password);
    }
}
