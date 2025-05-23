package com.myshop.internetshop.classes.services;

import com.myshop.internetshop.classes.dto.SafeUserDto;
import com.myshop.internetshop.classes.dto.UserDto;
import com.myshop.internetshop.classes.entities.User;
import com.myshop.internetshop.classes.enums.UserPermission;
import com.myshop.internetshop.classes.exceptions.ConflictException;
import com.myshop.internetshop.classes.exceptions.InternalServerErrorException;
import com.myshop.internetshop.classes.exceptions.NotFoundException;
import com.myshop.internetshop.classes.exceptions.ValidationException;
import com.myshop.internetshop.classes.repositories.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(UserDto userRequest) {
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
            logger.info("User with id {} deleted", id);
        } else {
            throw new NotFoundException("User you want to delete does not exist");
        }
    }

    public UserDto getUserById(Integer id) {
        if (userRepository.existsById(id)) {
            User user = userRepository.safeFindById(id);
            return new UserDto(user);
        } else {
            throw new NotFoundException("User does not exist");
        }
    }

    public UserDto updateUser(int id, UserDto userDto) {
        if (userRepository.existsById(id)) {
            User user = userRepository.findById(id);
            Optional<User> userCheck = userRepository.findByEmail(userDto.getEmail());
            if (userCheck.isPresent() && !Objects.equals(user.getId(),
                userCheck.get().getId())) {
                throw new ConflictException("User with that email already exists");
            }

            if (userDto.getName() != null) {
                user.setName(userDto.getName());
            }
            if (userDto.getEmail() != null) {
                user.setEmail(userDto.getEmail());
            }
            if (userDto.getPassword() != null) {
                user.setPassword(userDto.getPassword());
            }
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

    public SafeUserDto updateUserRole(int id, String role) {
        User user;
        if (userRepository.existsById(id)) {
            user = userRepository.findById(id);
        } else {
            throw new NotFoundException("User does not exist");
        }
        switch (role) {
            case "ADMIN":
                user.setPermission(UserPermission.ADMIN.getPermissionType());
                break;
            case "USER":
                user.setPermission(UserPermission.USER.getPermissionType());
                break;
            case "MERCHANT":
                user.setPermission(UserPermission.MERCHANT.getPermissionType());
                break;
            case "DELIVERY":
                user.setPermission(UserPermission.DELIVERY.getPermissionType());
                break;
            default:
                throw new ValidationException("Invalid role");
        }
        return new SafeUserDto(userRepository.save(user));
    }

    public Integer getIdByUsername(String name) {
        if (userRepository.existsByName(name)) {
            User user = userRepository.findByName(name);
            return user.getId();
        } else {
            throw new NotFoundException("User does not exist");
        }
    }

    public SafeUserDto getUserByName(String name) {
        if (userRepository.existsByName(name)) {
            User user = userRepository.findByName(name);
            return new SafeUserDto(user);
        }
        throw new InternalServerErrorException("Unexpected Error");
    }

    public List<SafeUserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).stream().map(SafeUserDto::new).toList();
    }

    public Integer getUserIdByName(String name) {
        return userRepository.findByName(name).getId();
    }

    public Integer getUsersCount() {
        return Math.toIntExact(userRepository.count());
    }
}
