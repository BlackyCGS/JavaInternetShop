package com.myshop.internetshop.classes.entities;

import com.myshop.internetshop.classes.enums.UserPermission;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int permission = UserPermission.USER_PERMISSION_REGULAR.getPermissionType();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Order> orders;

    public User() {
        orders = new ArrayList<>();
    }

    public User(String name, String email, String password, int permission) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.permission = permission;
    }

    public void addNewOrder(Order order) {
        orders.add(order);
    }
}
