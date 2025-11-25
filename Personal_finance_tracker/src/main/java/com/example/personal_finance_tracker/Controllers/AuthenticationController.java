package com.example.personal_finance_tracker.Controllers;

import com.example.personal_finance_tracker.DTOs.UserDTO;
import com.example.personal_finance_tracker.Services.TokenBlacklistService;
import com.example.personal_finance_tracker.Services.UserService;
import com.example.personal_finance_tracker.config.SecurityUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final UserService userService;
    private final TokenBlacklistService tokenBlacklistService;

    public AuthenticationController(UserService userService, TokenBlacklistService tokenBlacklistService) {
        this.userService = userService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userdto) {
        try {
            userService.addUser(userdto);
            SecurityContextHolder.clearContext();

            Map<String, String> response = new HashMap<>();
            response.put("message", "Registration successful");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Registration failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userdto, HttpServletResponse response) {

        System.out.println("Login attempt for user: " + userdto.getUsername());

        if (SecurityUtils.isAuthenticated()) {
            return ResponseEntity.ok(Map.of("message", "Already logged in", "redirect", "/api/dashboard"));
        }

        Map<String, String> tokens;
        try {
            tokens = userService.VerifyUser(userdto);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", ex.getMessage()));
        }

        String accessToken = tokens.get("accessToken");
        String refreshToken = tokens.get("refreshToken");

        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/api/auth/refresh")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        System.out.println("User: " + userdto.getUsername() + " logged in");

        return ResponseEntity.ok(Map.of(
                "accessToken", accessToken,
                "expiresIn", 900
        ));
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader)
    {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
        {
            String token = authorizationHeader.substring(7);

            tokenBlacklistService.blacklistToken(token);

            System.out.println("User logged out successfully. Token: " + token);

            return ResponseEntity.ok(Map.of("message", "Successfully logged out"));
        }

        return ResponseEntity.badRequest().body(Map.of("error", "Invalid token"));
    }




}
