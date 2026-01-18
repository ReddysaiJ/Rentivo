package com.rental.demo.controller;

import com.rental.demo.exception.UserAlreadyExistsException;
import com.rental.demo.model.*;
import com.rental.demo.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

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
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegisterUserRequest( "", "", "", "", ""));
        return "registerUser";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") RegisterUserRequest registerUserRequest,
                               BindingResult bindingResult,
                               @RequestParam("userPhoto") MultipartFile photo,
                               Model model
                               ) {
        if (bindingResult.hasErrors())
            return "registerUser";
        try {
            var cmd = new CreateUserCmd(null, registerUserRequest.username(), registerUserRequest.password(), registerUserRequest.email(), Role.ROLE_USER, registerUserRequest.phno(), "", registerUserRequest.drivingLicenseNo());
            userService.registerUser(cmd, photo);
            return "redirect:/login";
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("user", registerUserRequest);
            model.addAttribute("error", e.getMessage());
            return "registerUser";
        } catch (Exception e) {
            System.out.println(registerUserRequest);
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "registerUser";
        }
    }

    @GetMapping("/profile")
    public String userProfile(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CreateUserCmd cmd = userService.findByUsername(userDetails.getUsername());
        List<CarBooking> bookings = userService.findRecentBookingsByUser(cmd.id());
        model.addAttribute("user", cmd);
        model.addAttribute("bookings", bookings);
        
        return "profile";
    }
    
    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute("user") UpdateUserRequest updateUserRequest, BindingResult result,
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
        System.out.println(updateUserRequest);
        userService.updateUser(new CreateUserCmd(updateUserRequest.id(), updateUserRequest.username(), "", updateUserRequest.email(), updateUserRequest.role(), updateUserRequest.phno(), updateUserRequest.photo(), updateUserRequest.drivingLicenseNo()), photoFile);

        if ("ROLE_ADMIN".equals(userDetails.getRole()))
            return "redirect:/admin/manageUsers";
        else
            return "redirect:/user/profile";
    }
}