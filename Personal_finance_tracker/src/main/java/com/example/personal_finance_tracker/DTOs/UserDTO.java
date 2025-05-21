package com.example.personal_finance_tracker.DTOs;

import com.example.personal_finance_tracker.Models.Role;

import java.util.HashSet;
import java.util.Set;


public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Set<Long> accountIds;
    private Set<Role> roles;

    public UserDTO() {
    }

    public UserDTO(Set<Long> accountIds, String email, Long id, String password, Set<Role> roles, String username) {
        this.accountIds = accountIds;
        this.email = email;
        this.id = id;
        this.password = password;
        this.roles = roles;
        this.username = username;
    }

    public UserDTO(String username, String password, String email ) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public UserDTO(String username,String password) {
        this.username = username;
        this.password = password;
    }

    public UserDTO(String username,String password, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Long> getAccountIds() {
        return accountIds;
    }

    public void setAccountIds(Set<Long> accountIds) {
        this.accountIds = accountIds;
    }
}