package com.rawajsupermarket.auth.service;

import com.rawajsupermarket.auth.dto.request.LoginRequest;
import com.rawajsupermarket.auth.dto.request.RegisterRequest;
import com.rawajsupermarket.auth.dto.response.AuthResponse;

public interface AuthenticationService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    /** Second step of login when the first step returned twoFactorRequired=true. */
    AuthResponse completeTwoFactorLogin(String tempToken, String code);

    AuthResponse refreshToken(String refreshToken);

    void logout(String accessToken);
}