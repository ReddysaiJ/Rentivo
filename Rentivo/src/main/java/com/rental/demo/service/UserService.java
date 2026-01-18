package com.rental.demo.service;

import com.rental.demo.exception.UserAlreadyExistsException;
import com.rental.demo.exception.UserNotFoundException;
import com.rental.demo.model.CarBooking;
import com.rental.demo.model.CreateUserCmd;
import com.rental.demo.model.Role;
import com.rental.demo.model.User;
import com.rental.demo.repository.CarBookingRepo;
import com.rental.demo.repository.UserRepo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

    private final UserRepo userRepo;
	private final CarBookingRepo bookingRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final EntityMapper entityMapper;

    @Value("${app.images.dir}")
    private String DIR;

    public UserService(UserRepo userRepo, CarBookingRepo bookingRepo, PasswordEncoder passwordEncoder, EmailService emailService, EntityMapper entityMapper) {
        this.userRepo = userRepo;
        this.bookingRepo = bookingRepo;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.entityMapper = entityMapper;
    }

    public String saveImage(MultipartFile image) throws IOException {

        if (image == null || image.isEmpty()) {
            return "/images/users/default-profile.jpg";
        }

        Path uploadDir = Paths.get(DIR, "images", "users");
        Files.createDirectories(uploadDir);

        String fileName = System.currentTimeMillis() + "_" +
                image.getOriginalFilename().replaceAll("\\s+", "");

        Path filePath = uploadDir.resolve(fileName);

        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "/images/users/" + fileName;
    }


    public void registerUser(CreateUserCmd cmd, MultipartFile photo) throws IOException {
        if (userRepo.findByUsername(cmd.username()).isPresent()) {
            throw new UserAlreadyExistsException("Username already taken.");
        }
        if (userRepo.findByEmail(cmd.email()).isPresent()) {
            throw new UserAlreadyExistsException("Email already in use.");
        }
        if (userRepo.findByPhno(cmd.phno()).isPresent()) {
            throw new UserAlreadyExistsException("Phone number already in use.");
        }
        String photoUrl = saveImage(photo);
        var user = new User();
        user.setUsername(cmd.username());
        user.setPassword(passwordEncoder.encode(cmd.password()));
        user.setEmail(cmd.email());
        user.setPhno(cmd.phno());
        user.setRole(Role.ROLE_USER);
        user.setDrivingLicenseNo(cmd.drivingLicenseNo());
        user.setPhoto(photoUrl);

        emailService.sendWelcomeEmail(user.getEmail(), user.getUsername());
        userRepo.save(user);
    }

    public CreateUserCmd findByUsername(String username) {
        return userRepo.findByUsername(username)
                .map(entityMapper::toCreateUserCmd)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    public User updateUser(CreateUserCmd cmd, MultipartFile photo) throws IOException {
        User existingUser = userRepo.findById(cmd.id())
                .orElseThrow(() -> new UserNotFoundException("User not found with Id: " + cmd.id()));

        existingUser.setUsername(cmd.username());
        existingUser.setEmail(cmd.email());
        existingUser.setPhno(cmd.phno());
        existingUser.setRole(cmd.role());
        existingUser.setDrivingLicenseNo(cmd.drivingLicenseNo());

        if (photo != null && !photo.isEmpty()) {
            String photoUrl = saveImage(photo);
            existingUser.setPhoto(photoUrl);
        }
        emailService.sendUpdateEmail(existingUser.getEmail(), existingUser.getUsername());
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
