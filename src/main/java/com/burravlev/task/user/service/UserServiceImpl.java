package com.burravlev.task.user.service;

import com.burravlev.task.user.exception.UserNotFoundException;
import com.burravlev.task.user.model.UserModel;
import com.burravlev.task.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public UserModel findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }


}
