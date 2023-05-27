package com.burravlev.task.token.service;

import com.burravlev.task.token.model.Token;

import java.util.List;
import java.util.Optional;

public interface TokenService {
    Optional<Token> findByToken(String token);

    Token save(Token token);

    List<Token> saveAll(List<Token> tokens);

    List<Token> findAllValidTokensByUserId(Long id);
}
