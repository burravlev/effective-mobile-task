package com.burravlev.task.user.service;

import com.burravlev.task.user.dto.UpdateRequest;
import com.burravlev.task.user.exception.EmailIsAlreadyTakenException;
import com.burravlev.task.user.exception.UserNotFoundException;
import com.burravlev.task.user.exception.UsernameIsAlreadyTakenException;
import com.burravlev.task.user.model.UserModel;
import com.burravlev.task.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserModel findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserModel findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserModel save(UserModel user) {
        if (repository.existsByEmail(user.getEmail()))
            throw new EmailIsAlreadyTakenException("This email already registered");
        if (repository.existsByUsername(user.getUsername()))
            throw new UsernameIsAlreadyTakenException("Username id already taken");
        return repository.save(user);
    }

    @Override
    public UserModel findById(Long id) {
        return repository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserModel update(String username, UpdateRequest request) {
        UserModel user = findByUsername(username);
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        return repository.save(user);
    }

}
