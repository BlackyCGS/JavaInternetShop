
package com.myshop.internetshop.classes.controllers;

import com.myshop.internetshop.classes.dto.UserDto;
import com.myshop.internetshop.classes.entities.User;
import com.myshop.internetshop.classes.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Manage Users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get user data by id")
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") Integer id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Get user data by name")
    @GetMapping("/{name}")
    @PreAuthorize("hasRole('ADMIN') || #name == authentication.name")
    public UserDto getUserByName(@PathVariable("name") String name) {
        return userService.getUserByName(name);
    }

    @Operation(summary = "Create user using json with login password and email")
    @PostMapping("/create")
    public User createUser(@Valid @RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @Operation(summary = "Update user data using id and json with new data")
    @PutMapping("/update/{id}")
    public UserDto updateUser(@Valid @RequestBody UserDto userDto, @PathVariable int id) {
        return userService.updateUser(id, userDto);
    }

    @Operation(summary = "Delete user by id")
    @DeleteMapping("/delete/{id}")
    public HttpEntity<Object> deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/role/{id}/{roleType}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto updateUserRole(@PathVariable int id, @PathVariable String roleType) {
        return userService.updateUserRole(id, roleType);

    }
}
