package com.rawajsupermarket.service;

import com.rawajsupermarket.dto.request.LoginRequest;
import com.rawajsupermarket.dto.request.RegisterRequest;
import com.rawajsupermarket.dto.response.AuthResponse;

public interface AuthenticationService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    /** Second step of login when the first step returned twoFactorRequired=true. */
    AuthResponse completeTwoFactorLogin(String tempToken, String code);

    AuthResponse refreshToken(String refreshToken);

    void logout(String accessToken);
}