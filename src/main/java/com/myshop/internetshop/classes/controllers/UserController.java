package com.myshop.internetshop.classes.controllers;

import com.myshop.internetshop.classes.dto.UserDto;
import com.myshop.internetshop.classes.dto.UserRequest;
import com.myshop.internetshop.classes.entities.User;
import com.myshop.internetshop.classes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public User createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<Object> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PutMapping("/update/{id}")
    public UserDto updateUser(@RequestBody UserRequest userRequest, @PathVariable int id) {
        return userService.updateUser(id, userRequest);
    }

}
