package com.burravlev.task.user.service;

import com.burravlev.task.user.domain.dto.PasswordUpdateRequest;
import com.burravlev.task.user.domain.dto.UserInfoUpdateRequest;
import com.burravlev.task.user.domain.model.UserModel;

public interface UserService {
    UserModel findById(Long id);

    UserModel findByEmail(String email);

    UserModel findBySub(String sub);

    UserModel findByUsername(String username);

    UserModel save(UserModel user);

    UserModel update(Long id, UserInfoUpdateRequest request);

    UserModel updatePassword(Long id, PasswordUpdateRequest request);
}
