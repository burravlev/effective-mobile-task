package com.burravlev.task.user.service;

import com.burravlev.task.user.domain.model.PasswordUpdateRequest;
import com.burravlev.task.user.domain.model.UserInfoUpdateRequest;
import com.burravlev.task.user.domain.entity.UserEntity;

public interface UserService {
    UserEntity findById(Long id);

    UserEntity findByEmail(String email);

    UserEntity findBySub(String sub);

    UserEntity findByUsername(String username);

    UserEntity save(UserEntity user);

    UserEntity update(Long id, UserInfoUpdateRequest request);

    UserEntity updatePassword(Long id, PasswordUpdateRequest request);
}
