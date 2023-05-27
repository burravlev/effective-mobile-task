package com.burravlev.task.token.service;

import com.burravlev.task.token.model.Token;
import com.burravlev.task.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository repository;

    @Override
    public Optional<Token> findByToken(String token) {
        return repository.findByToken(token);
    }

    @Override
    public Token save(Token token) {
        return repository.save(token);
    }

    @Override
    public List<Token> saveAll(List<Token> tokens) {
        return repository.saveAll(tokens);
    }

    @Override
    public List<Token> findAllValidTokensByUserId(Long id) {
        return repository.findAllValidTokens(id);
    }
}
