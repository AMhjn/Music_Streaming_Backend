package com.music_streaming.controllers;

import com.music_streaming.models.User;
import com.music_streaming.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        return userService.registerUser(user.getUsername(), user.getPassword());
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {
        return userService.authenticateUser(user.getUsername(), user.getPassword());
    }
}
