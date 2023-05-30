package com.burravlev.task.auth.service;

import com.burravlev.task.auth.model.AuthenticationRequest;
import com.burravlev.task.auth.model.AuthenticationResponse;
import com.burravlev.task.auth.model.RegistrationRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponse register(RegistrationRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void refreshToken(HttpServletRequest req, HttpServletResponse res) throws IOException;
}
