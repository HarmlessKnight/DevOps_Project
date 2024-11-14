package com.example.personal_finance_tracker.Controllers;

import com.example.personal_finance_tracker.DTOs.UserDTO;
import com.example.personal_finance_tracker.Services.TokenBlacklistService;
import com.example.personal_finance_tracker.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RegisterController {

    private final UserService userService;
    private final TokenBlacklistService tokenBlacklistService;

    public RegisterController(UserService userService, TokenBlacklistService tokenBlacklistService) {
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

    @GetMapping("/login")
    public ResponseEntity<?> checkLoginStatus() {

        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();

        if (currentAuth != null && currentAuth.isAuthenticated() && !(currentAuth instanceof AnonymousAuthenticationToken)) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Already logged in");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Please log in.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse); // Only trigger after login attempts
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userdto) {
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        if (currentAuth != null && currentAuth.isAuthenticated() && !(currentAuth instanceof AnonymousAuthenticationToken)) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Already logged in");
            response.put("redirect", "/api/dashboard");
            return ResponseEntity.ok(response);
        }

        String token = userService.VerifyUser(userdto);
        if (token.startsWith("Failed")) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", token);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("token", token);
        System.out.println("User:" + userdto.getUsername()+ " logged in");
        return ResponseEntity.ok(successResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            System.out.println("User logged out successfully. Token: " + token);
            tokenBlacklistService.blacklistToken(token);
            return ResponseEntity.ok("Successfully logged out");
        }
        return ResponseEntity.badRequest().body("Invalid token");
    }


}
