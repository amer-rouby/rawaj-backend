package com.rawajtechshop.auth.controller;

import com.rawajtechshop.common.dto.ApiResponse;
import com.rawajtechshop.auth.dto.response.AuthResponse;
import com.rawajtechshop.auth.service.AuthenticationService;
import com.rawajtechshop.auth.dto.response.ExtendSessionResponse;
import com.rawajtechshop.auth.dto.request.LoginRequest;
import com.rawajtechshop.auth.dto.request.RegisterRequest;
import com.rawajtechshop.auth.service.SessionService;
import com.rawajtechshop.auth.dto.response.SessionStatusResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final SessionService sessionService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/2fa/login")
    public ResponseEntity<AuthResponse> completeTwoFactorLogin(@RequestBody Map<String, String> body) {
        AuthResponse response = authenticationService.completeTwoFactorLogin(
                body.get("tempToken"), body.get("code"));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestParam String refreshToken) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        authenticationService.logout(authHeader);
        return ResponseEntity.ok(ApiResponse.success("Logged out successfully"));
    }

    @GetMapping("/session-status")
    public ResponseEntity<SessionStatusResponse> getSessionStatus(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        SessionStatusResponse status = sessionService.getSessionStatus(token);
        return ResponseEntity.ok(status);
    }

    @PostMapping("/extend-session")
    public ResponseEntity<ExtendSessionResponse> extendSession(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        ExtendSessionResponse response = sessionService.extendSession(token);
        return ResponseEntity.ok(response);
    }
}
