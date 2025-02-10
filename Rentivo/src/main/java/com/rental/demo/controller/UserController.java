package com.rental.demo.controller;

import com.rental.demo.model.CarBooking;
import com.rental.demo.model.User;
import com.rental.demo.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import com.rental.demo.model.UserDetailsImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "registerUser";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, 
                               @RequestParam("userPhoto") MultipartFile photo, 
                               Model model) {
        try {
            userService.registerUser(user, photo);
            return "redirect:/user/profile";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "registerUser";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred during registration. Please try again.");
            return "registerUser";
        }
    }

    @GetMapping("/profile")
    public String userProfile(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        List<CarBooking> bookings = userService.findRecentBookingsByUser(user.getId());
        
        model.addAttribute("user", user);
        model.addAttribute("bookings", bookings);
        
        return "profile";
    }
    
    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult result,
                             @RequestParam(value = "photoFile", required = false) MultipartFile photoFile,
                             RedirectAttributes redirectAttributes,
                             @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Validation error");
            if ("ADMIN".equals(userDetails.getRole()))
                return "redirect:/admin/manageUsers";
            else
                return "redirect:/user/profile";
        }

        userService.updateUser(user, photoFile);

        if ("ADMIN".equals(userDetails.getRole()))
            return "redirect:/admin/manageUsers";
        else
            return "redirect:/user/profile";
    }


}
