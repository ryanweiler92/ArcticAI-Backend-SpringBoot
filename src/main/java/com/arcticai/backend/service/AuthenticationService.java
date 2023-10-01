package com.arcticai.backend.service;

import com.arcticai.backend.dao.response.JwtAuthenticationResponse;
import com.arcticai.backend.dao.request.SignUpRequest;
import com.arcticai.backend.dao.request.SigninRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
