package com.myshop.internetshop.classes.repositories;

import com.myshop.internetshop.classes.entities.RefreshToken;

import java.util.Date;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findById(Integer id);

    boolean existsByToken(String refreshToken);

    RefreshToken findByToken(String refreshToken);

    void deleteByUserId(int id);

    void deleteByToken(String refreshToken);

    void deleteAllByUserId(int id);

    void deleteAllByExpirationTimeBefore(Date expirationTime);
}
