package com.burravlev.task.user.service;

import com.burravlev.task.user.dto.PasswordUpdateRequest;
import com.burravlev.task.user.dto.UpdateRequest;
import com.burravlev.task.user.model.UserModel;

public interface UserService {
    UserModel findById(Long id);

    UserModel findByEmail(String email);

    UserModel findByUsername(String username);

    UserModel save(UserModel user);

    UserModel update(String username, UpdateRequest request);

}
