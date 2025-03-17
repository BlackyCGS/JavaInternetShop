package com.myshop.internetshop.classes.dto;

import com.myshop.internetshop.classes.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String name;
    private String email;
    private String password;

    public UserRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserRequest() {
        /*Default Constructor*/
    }
    public User parseToUser() {
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setPassword(this.password);
        return user;
    }
}
