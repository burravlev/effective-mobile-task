package com.burravlev.task.user.service;

import com.burravlev.task.auth.exception.WrongCredentialsException;
import com.burravlev.task.exception.NotFoundException;
import com.burravlev.task.user.domain.model.PasswordUpdateRequest;
import com.burravlev.task.user.domain.model.UserInfoUpdateRequest;
import com.burravlev.task.user.exception.EmailIsAlreadyTakenException;
import com.burravlev.task.user.exception.UserNotFoundException;
import com.burravlev.task.user.exception.UsernameIsAlreadyTakenException;
import com.burravlev.task.user.domain.entity.UserEntity;
import com.burravlev.task.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email: " + email + " not found"));
    }

    @Override
    public UserEntity findBySub(String sub) {
        return repository.findById(Long.parseLong(sub))
                .orElseThrow(() -> new NotFoundException("User: " + sub + " not found"));
    }

    @Override
    public UserEntity findByUsername(String username) {
        return repository.findByPublicUsername(username)
                .orElseThrow(() -> new NotFoundException("User: " + username + " not found"));
    }

    @Override
    @Transactional
    public UserEntity save(UserEntity user) {
        if (repository.existsByEmail(user.getEmail()))
            throw new EmailIsAlreadyTakenException("This email already registered");
        if (repository.existsByPublicUsername(user.getPublicUsername()))
            throw new UsernameIsAlreadyTakenException("Username id already taken");
        return repository.save(user);
    }

    @Override
    public UserEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserEntity update(Long id, UserInfoUpdateRequest request) {
        UserEntity user = this.findById(id);
        if (request.getUsername() != null)
            user.setPublicUsername(request.getUsername());
        if (request.getEmail() != null)
            user.setEmail(request.getEmail());
        if (request.getFirstName() != null)
            user.setFirstName(request.getFirstName());
        if (request.getLastName() != null)
            user.setLastName(request.getLastName());

        return repository.save(user);
    }

    @Override
    @Transactional
    public UserEntity updatePassword(Long id, PasswordUpdateRequest request) {
        UserEntity user = findById(id);
        System.out.println(request.getOldPassword());
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword()))
            throw new WrongCredentialsException("Old password doesn't match");
        System.out.println(request.getNewPassword());
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        return repository.save(user);
    }
}
