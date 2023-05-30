package com.burravlev.task.auth.service;

import com.burravlev.task.auth.model.AuthenticationRequest;
import com.burravlev.task.auth.model.AuthenticationResponse;
import com.burravlev.task.auth.model.RegistrationRequest;
import com.burravlev.task.auth.exception.WrongCredentialsException;
import com.burravlev.task.jwt.service.JwtService;
import com.burravlev.task.token.model.Token;
import com.burravlev.task.token.model.TokenType;
import com.burravlev.task.token.service.TokenService;
import com.burravlev.task.user.domain.entity.UserEntity;
import com.burravlev.task.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegistrationRequest request) {
        UserEntity user = UserEntity.builder()
                .publicUsername(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        UserEntity saved = userService.save(user);
        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(saved, token);
        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        final UserEntity user;

        if (request.getUsername() != null)
            user = userService.findByUsername(request.getUsername());
        else if (request.getEmail() != null)
            user = userService.findByEmail(request.getEmail());
        else throw new WrongCredentialsException("User doesn't exists");

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new WrongCredentialsException("Wrong password");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        request.getPassword()
                )
        );

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(UserEntity user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .type(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenService.save(token);
    }

    @Override
    public void refreshToken(HttpServletRequest req, HttpServletResponse res) throws IOException {
        final String authHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return;
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            UserEntity user = userService.findByEmail(userEmail);
            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                AuthenticationResponse authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(res.getOutputStream(), authResponse);
            }
        }
    }

    private void revokeAllUserTokens(UserEntity user) {
        List<Token> validUserTokens = tokenService.findAllValidTokensByUserId(user.getId());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenService.saveAll(validUserTokens);
    }
}
