package com.arcticai.backend.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.arcticai.backend.entities.User;

public interface UserService {
    UserDetailsService userDetailsService();
    User findByEmail(String email);
}
