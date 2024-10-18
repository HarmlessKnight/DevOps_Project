package com.example.personal_finance_tracker.Services;

import com.example.personal_finance_tracker.Exceptions.InvalidUserException;
import com.example.personal_finance_tracker.Models.User;
import com.example.personal_finance_tracker.Models.UserPrincipal;
import com.example.personal_finance_tracker.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.personal_finance_tracker.Exceptions.InvalidUserException;
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow();

        return new UserPrincipal(user);
    }
}
