package com.burravlev.task.user.repository;

import com.burravlev.task.user.domain.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPublicUsername(String username);

    Optional<UserModel> findByPublicUsername(String username);

}
