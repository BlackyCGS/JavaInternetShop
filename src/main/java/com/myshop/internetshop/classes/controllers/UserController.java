
package com.myshop.internetshop.classes.controllers;

import com.myshop.internetshop.classes.dto.SafeUserDto;
import com.myshop.internetshop.classes.dto.UserDto;
import com.myshop.internetshop.classes.entities.User;
import com.myshop.internetshop.classes.exceptions.ForbiddenException;
import com.myshop.internetshop.classes.services.JwtService;
import com.myshop.internetshop.classes.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Manage Users")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Operation(summary = "Get user data by id")
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") Integer id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Get user data by name")
    @GetMapping("/{name}")
    @PreAuthorize("hasRole('ADMIN') || #name == authentication.name")
    public SafeUserDto getUserByName(@PathVariable("name") String name) {
        return userService.getUserByName(name);
    }

    @Operation(summary = "Get user data by name")
    @GetMapping("/profile")
    public SafeUserDto getUserByName(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }
        if(token != null) {
            String name = jwtService.extractUsername(token);
            return userService.getUserByName(name);
        }
        throw new ForbiddenException("Forbidden");
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
    @PreAuthorize("hasRole('ADMIN')")
    public HttpEntity<Object> deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/role/{id}/{roleType}")
    @PreAuthorize("hasRole('ADMIN')")
    public SafeUserDto updateUserRole(@PathVariable int id,
                                      @PathVariable String roleType) {
        return userService.updateUserRole(id, roleType);

    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public List<SafeUserDto> getAllUsers(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "20") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return userService.getAllUsers(pageable);
    }

    @Operation(summary = "Get total number of users")
    @GetMapping("/amount")
    public ResponseEntity<Integer> getTotalUsers() {
        return ResponseEntity.ok(userService.getUsersCount());
    }
}
