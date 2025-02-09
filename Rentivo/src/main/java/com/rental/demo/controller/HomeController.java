package com.rental.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

	@GetMapping("/login")
    public String login() {
        return "login";
    }
    
	@GetMapping("/")
	public String home(Authentication authentication) {
	    if (authentication != null && authentication.getAuthorities().stream()
	            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
	        return "adminhome";
	    }
	    return "userhome";
	}



}
