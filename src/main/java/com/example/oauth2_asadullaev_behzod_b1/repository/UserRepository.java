package com.example.oauth2_asadullaev_behzod_b1.repository;

import com.example.oauth2_asadullaev_behzod_b1.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("select u from UserEntity u where u.username = ?1")
    Optional<UserEntity> findByUsername(String username);

    @Query("select (count(u) > 0) from UserEntity u where u.username = ?1")
    boolean existsByUsername(String username);
}
