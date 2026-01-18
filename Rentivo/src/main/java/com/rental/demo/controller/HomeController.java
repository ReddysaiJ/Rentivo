package com.rental.demo.controller;

import com.rental.demo.model.User;
import com.rental.demo.service.EmailService;
import com.rental.demo.service.OtpService;
import com.rental.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class HomeController {

	UserService userService;
	EmailService emailService;
	OtpService otpService;

	@GetMapping("/login")
    public String login(Authentication authentication) {
        if(authentication != null)
			return "redirect:/";
		return "login";
    }
    
	@GetMapping("/")
	public String home() {
	    return "home";
	}

	@GetMapping("/forgot-password")
	public String displayForgotPasswordPage() {
		return "forgot-password";
	}

	@PostMapping("/forgot-password/send-otp")
	public String sendOtp(@RequestParam("email") String email, Model model) {
		User user = userService.findByEmail(email);
		if (user == null) {
			model.addAttribute("step", "send");
			model.addAttribute("error", "No account found with that email address.");
			return "forgot-password";
		}
		String otp = otpService.generateOTP(email);
		emailService.sendOTPEmail(email, "Password Reset OTP", "Your OTP for password reset is: " + otp);
		model.addAttribute("email", email);
		model.addAttribute("step", "verify");
		return "forgot-password";
	}

	@PostMapping("/forgot-password/verify-otp")
	public String verifyOtp(@RequestParam("email") String email,
							@RequestParam("otp") String otp,
							Model model) {
		boolean valid = otpService.validateOTP(email, otp);
		if (!valid) {
			model.addAttribute("email", email);
			model.addAttribute("step", "verify");
			model.addAttribute("error", "Invalid OTP provided.");
			return "forgot-password";
		}
		model.addAttribute("email", email);
		model.addAttribute("step", "reset");
		return "forgot-password";
	}

	@PostMapping("/forgot-password/reset")
	public String resetPassword(@RequestParam("email") String email,
								@RequestParam("newPassword") String newPassword,
								@RequestParam("confirmPassword") String confirmPassword,
								Model model) {
		if (!newPassword.equals(confirmPassword)) {
			model.addAttribute("email", email);
			model.addAttribute("step", "reset");
			model.addAttribute("error", "Passwords do not match.");
			return "forgot-password";
		}
		userService.updatePassword(email, newPassword);
		return "redirect:/login?message=Password+successfully+reset";
	}

	@GetMapping("/logout")
	public String logout() {
		return "redirect:/";
	}
}
