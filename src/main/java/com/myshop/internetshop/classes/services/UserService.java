package com.myshop.internetshop.classes.services;

import com.myshop.internetshop.classes.dto.UserDto;
import com.myshop.internetshop.classes.dto.UserRequest;
import com.myshop.internetshop.classes.entities.User;
import com.myshop.internetshop.classes.exceptions.ConflictException;
import com.myshop.internetshop.classes.exceptions.NotFoundException;
import com.myshop.internetshop.classes.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new ConflictException("User with that email already exists");
        }
        if (userRepository.existsByName(userRequest.getName())) {
            throw new ConflictException("User with that name already exists");
        }
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new NotFoundException("User you want to delete does not exist");
        }
    }

    public UserDto getUserById(int id) {
        if (userRepository.existsById(id)) {
            return new UserDto(userRepository.findById(id));
        } else {
            throw new NotFoundException("User does not exist");
        }
    }

    public UserDto updateUser(int id, UserRequest userRequest) {
        if (userRepository.existsById(id)) {
            User user = userRepository.findById(id);
            if (userRequest.getName() != null) {
                user.setName(userRequest.getName());
            }
            if (userRequest.getEmail() != null) {
                user.setEmail(userRequest.getEmail());
            }
            if (userRequest.getPassword() != null) {
                user.setPassword(userRequest.getPassword());
            }
            userRepository.save(user);
            return new UserDto(userRepository.save(user));
        } else {
            throw new NotFoundException("User you want to update does not exist");
        }
    }

    public boolean existsById(int id) {
        return userRepository.existsById(id);
    }

    public User findByUserId(int id) {
        if (userRepository.existsById(id)) {
            return userRepository.findById(id);
        } else {
            throw new NotFoundException("User does not exists");
        }
    }
}
