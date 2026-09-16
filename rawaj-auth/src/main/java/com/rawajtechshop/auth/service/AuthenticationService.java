package com.rawajtechshop.auth.service;

import com.rawajtechshop.auth.dto.response.AuthResponse;
import com.rawajtechshop.auth.dto.request.LoginRequest;
import com.rawajtechshop.auth.dto.request.RegisterRequest;

public interface AuthenticationService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    /** Second step of login when the first step returned twoFactorRequired=true. */
    AuthResponse completeTwoFactorLogin(String tempToken, String code);

    AuthResponse refreshToken(String refreshToken);

    void logout(String accessToken);
}
