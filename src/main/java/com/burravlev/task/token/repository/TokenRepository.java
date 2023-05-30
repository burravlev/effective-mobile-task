package com.burravlev.task.token.repository;

import com.burravlev.task.token.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("SELECT t FROM Token t INNER JOIN UserEntity u ON t.user.id = u.id WHERE u.id = :id AND (t.expired = false OR t.revoked = false)")
    List<Token> findAllValidTokens(Long id);

    Optional<Token> findByToken(String token);
}
