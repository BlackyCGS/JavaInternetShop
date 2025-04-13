package com.myshop.internetshop.classes.services;

import com.myshop.internetshop.classes.dto.LoginRequest;
import com.myshop.internetshop.classes.dto.UserDto;
import com.myshop.internetshop.classes.entities.User;
import com.myshop.internetshop.classes.exceptions.ConflictException;
import com.myshop.internetshop.classes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        private final AuthenticationManager authenticationManager;

        @Autowired
        public AuthService(
                UserRepository userRepository,
                AuthenticationManager authenticationManager,
                PasswordEncoder passwordEncoder
        ) {
            this.authenticationManager = authenticationManager;
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
        }

        public User signUp(UserDto userRequest) {
            if (userRepository.existsByEmail(userRequest.getEmail())) {
                throw new ConflictException("User with that email already exists");
            }
            if (userRepository.existsByName(userRequest.getName())) {
                throw new ConflictException("User with that name already exists");
            }
            User user = new User();
            user.setName(userRequest.getName());
            user.setEmail(userRequest.getEmail());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            return userRepository.save(user);
        }

        public User authenticate(LoginRequest input) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );

            return userRepository.findByEmail(input.getEmail())
                    .orElseThrow();
        }
}

