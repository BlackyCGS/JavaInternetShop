package com.myshop.internetshop.classes.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
import java.util.Date;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
public class RefreshToken {
    @Id
    private String id;

    @NonNull
    private int userId;

    @NotNull
    @Column(name = "refresh_token")
    private String token;

    @NotNull
    private Date expirationTime;

    private boolean expired;
}
