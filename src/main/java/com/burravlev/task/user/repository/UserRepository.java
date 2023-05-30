package com.burravlev.task.user.repository;

import com.burravlev.task.user.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPublicUsername(String username);

    Optional<UserEntity> findByPublicUsername(String username);

}
