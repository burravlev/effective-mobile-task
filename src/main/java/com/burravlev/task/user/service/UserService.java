package com.burravlev.task.user.service;

import com.burravlev.task.user.model.UserModel;

public interface UserService {
    UserModel findByEmail(String email);
}
