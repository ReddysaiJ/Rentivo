package com.rental.demo.service;

import com.rental.demo.exception.UserAlreadyExistsException;
import com.rental.demo.exception.UserNotFoundException;
import com.rental.demo.model.CarBooking;
import com.rental.demo.model.User;
import com.rental.demo.repository.CarBookingRepo;
import com.rental.demo.repository.UserRepo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {
	
	@Autowired
    private UserRepo userRepo;
	
	@Autowired
	private CarBookingRepo bookingRepo;
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    private EmailService emailService;
    
    private static final String IMAGE_UPLOAD_DIR = "src/main/webapp/images/users/";
    
    public String saveImage(MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            return null;
        }
        File directory = new File(IMAGE_UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String imageName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path imagePath = Paths.get(IMAGE_UPLOAD_DIR + imageName);
        Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
        return "/images/users/" + imageName;
    }

    public User registerUser(User user, MultipartFile photo) throws IOException {
        String photoUrl = saveImage(photo);
        user.setPhoto(photoUrl);
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username already taken.");
        }
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already in use.");
        }
        if (userRepo.findByPhno(user.getPhno()).isPresent()) {
            throw new UserAlreadyExistsException("Phone number already in use.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setRole("USER");
        emailService.sendWelcomeEmail(user.getEmail(), user.getUsername());
        return userRepo.save(user);
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    public String savePhoto(MultipartFile photo) throws IOException {
    	if (photo.isEmpty()) {
    	    return null;
    	}

    	File directory = new File("src/main/webapp/images/users/");
    	if (!directory.exists()) {
    	    directory.mkdirs();
    	}

    	String filename = photo.getOriginalFilename();
    	Path filePath = Paths.get(directory.getAbsolutePath(), filename);
    	Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

    	return "/images/users/" + filename;

    }

    public User updateUser(User user, MultipartFile photo) throws IOException {
        User existingUser = userRepo.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + user.getId()));

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhno(user.getPhno());
        existingUser.setRole(user.getRole());
        existingUser.setDrivingLicenseNo(user.getDrivingLicenseNo());

        if (photo != null && !photo.isEmpty()) {
            String photoUrl = saveImage(photo);
            existingUser.setPhoto(photoUrl);
        }
        emailService.sendUpdateEmail(user.getEmail(), user.getUsername());
        return userRepo.save(existingUser);
    }

    public List<CarBooking> findRecentBookingsByUser(Long userId) {
        return bookingRepo.findTop5ByCustomerIdOrderByStartDateDesc(userId);
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    public void updatePassword(String email, String newPassword) {
        userRepo.findByEmail(email).ifPresent(user -> {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepo.save(user);
        });
    }
}
