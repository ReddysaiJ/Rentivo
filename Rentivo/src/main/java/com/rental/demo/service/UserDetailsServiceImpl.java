package com.rental.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rental.demo.exception.UserNotFoundException;
import com.rental.demo.model.User;
import com.rental.demo.model.UserDetailsImpl;
import com.rental.demo.repository.UserRepo;

@Service
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
        		.orElseThrow(() -> new UserNotFoundException("User not found with Username: " + username));
        return new UserDetailsImpl(user);
    }
}
