package com.myshop.internetshop.classes.controllers;

import com.myshop.internetshop.classes.dto.LoginRequest;
import com.myshop.internetshop.classes.dto.UserDto;
import com.myshop.internetshop.classes.entities.User;
import com.myshop.internetshop.classes.exceptions.ForbiddenException;
import com.myshop.internetshop.classes.services.AuthService;
import com.myshop.internetshop.classes.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtService jwtService;

    private final AuthService authService;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    @Value("${security.jwt.refresh.expiration-time}")
    private long refreshExpirationTime;

    @Autowired
    public AuthController(JwtService jwtService, AuthService authenticationService) {
        this.jwtService = jwtService;
        this.authService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody UserDto registerUserDto) {
        User registeredUser = authService.signUp(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody LoginRequest loginUserDto) {
        User authenticatedUser = authService.authenticate(loginUserDto);

        List<String> jwtTokens = jwtService.generateTokenPair(authenticatedUser);

        return prepareAndReturnCookies(jwtTokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtr".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                }
            }
        }
        if (refreshToken == null) {
            throw new ForbiddenException("You are not logged in");
        }

        List<String> jwtTokens = jwtService.updateTokenPair(refreshToken);

        return prepareAndReturnCookies(jwtTokens);
    }

    private ResponseEntity<String> prepareAndReturnCookies(List<String> jwtTokens) {
        ResponseCookie jwtAccess = ResponseCookie.from("jwt", jwtTokens.get(0))
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(jwtExpiration/1000)
                .build();

        ResponseCookie jwtRefresh = ResponseCookie.from("jwtr", jwtTokens.get(1))
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(refreshExpirationTime/1000)
                .build();
        return ResponseEntity.ok().
                header(HttpHeaders.SET_COOKIE, jwtAccess.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefresh.toString())
                .body(jwtTokens.toString());
    }

    @PostMapping("/revoke/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> revoke(@PathVariable int id) {
        jwtService.revokeUser(id);
        return ResponseEntity.ok().body("Session revoked");
    }

    @GetMapping("/logout/success")
    public ResponseEntity<String> logoutSuccess() {
        return ResponseEntity.ok().body("Logout successful");
    }
}
