package com.myshop.internetshop.classes.repositories;

import com.myshop.internetshop.classes.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findById(int id);



    boolean existsByEmail(String email);

    boolean existsByName(String username);
}
