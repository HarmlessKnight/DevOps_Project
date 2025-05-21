package com.example.personal_finance_tracker.Services.impl;

import com.example.personal_finance_tracker.DTOs.UserDTO;
import com.example.personal_finance_tracker.Exceptions.InvalidUserException;
import com.example.personal_finance_tracker.Models.Role;
import com.example.personal_finance_tracker.Models.User;
import com.example.personal_finance_tracker.Repositories.RoleRepository;
import com.example.personal_finance_tracker.Repositories.UserRepository;
import com.example.personal_finance_tracker.Services.UserService;
import com.example.personal_finance_tracker.Exceptions.InvalidUserException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceimpl implements UserService {


    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JWTService jwtService;


    public UserServiceimpl(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new InvalidUserException("User id not found: " + id));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findAll().stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
    }

    @Override
    public User addUser(UserDTO userDTO) {

        Role role = roleRepository.findByName("ROLE_USER").orElseGet(() -> {
            Role newrole = new Role("ROLE_USER");
            return roleRepository.save(newrole);
        });

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Set<Role> roles = new HashSet<>();

        roles.add(role);

        user.setRoles(roles);

        return userRepository.save(user);
    }



    @Override
    public User updateUser(Long id, UserDTO userDTO) {
        User currentUser = userRepository.findById(id).orElseThrow(() -> new InvalidUserException("User id not found: " + id));

        currentUser.setUsername(userDTO.getUsername());
        currentUser.setEmail(userDTO.getEmail());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            currentUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        userRepository.save(currentUser);
        return currentUser;
    }


    @Override
    public User deleteUser(Long id) {
        User curuser = userRepository.findById(id).orElseThrow(() -> new InvalidUserException("User id not found: " + id));
        userRepository.delete(curuser);
        return curuser;
    }

    @Override
    public String VerifyUser(UserDTO userdto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userdto.getUsername(), userdto.getPassword())
            );

            if (authentication.isAuthenticated()) {

                UserDetails userDetails = (UserDetails) authentication.getPrincipal();

                return jwtService.generateToken(userDetails);
            } else {
                return "Authentication failed for user: " + userdto.getUsername();
            }
        } catch (Exception e) {
            return "Failed to login user: " + userdto.getUsername() + ". Error: " + e.getMessage();
        }
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new InvalidUserException("User name not found: " + username));
    }


}
