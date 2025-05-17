package com.myshop.internetshop.classes.repositories;

import com.myshop.internetshop.classes.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findById(int id);

    @Query("select new User(u.id, u.email, u.name) from User u where "
            + "u.id = :id")
    User safeFindById(@Param("id") Integer id);

    User findByName(String name);

    boolean existsByEmail(String email);

    boolean existsByName(String username);

    Optional<User> findByEmail(String email);

    Integer getIdByName(String name);
}
