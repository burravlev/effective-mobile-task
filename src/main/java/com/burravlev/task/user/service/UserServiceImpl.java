package com.burravlev.task.user.service;

import com.burravlev.task.auth.exception.WrongCredentialsException;
import com.burravlev.task.user.domain.dto.PasswordUpdateRequest;
import com.burravlev.task.user.domain.dto.UpdateRequest;
import com.burravlev.task.user.exception.EmailIsAlreadyTakenException;
import com.burravlev.task.user.exception.UserNotFoundException;
import com.burravlev.task.user.exception.UsernameIsAlreadyTakenException;
import com.burravlev.task.user.domain.model.UserModel;
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
    public UserModel findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserModel findBySub(String sub) {
        return repository.findById(Long.parseLong(sub))
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserModel findByUsername(String username) {
        return repository.findByPublicUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional
    public UserModel save(UserModel user) {
        if (repository.existsByEmail(user.getEmail()))
            throw new EmailIsAlreadyTakenException("This email already registered");
        if (repository.existsByPublicUsername(user.getPublicUsername()))
            throw new UsernameIsAlreadyTakenException("Username id already taken");
        return repository.save(user);
    }

    @Override
    public UserModel findById(Long id) {
        return repository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserModel update(Long id, UpdateRequest request) {
        UserModel user = this.findById(id);
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
    public UserModel updatePassword(Long id, PasswordUpdateRequest request) {
        UserModel user = findById(id);
        System.out.println(request.getOldPassword());
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword()))
            throw new WrongCredentialsException("Old password doesn't match");
        System.out.println(request.getNewPassword());
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        return repository.save(user);
    }
}
