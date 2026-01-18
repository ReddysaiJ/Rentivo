package com.rental.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.rental.demo.model.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rental.demo.exception.UserNotFoundException;
import com.rental.demo.repository.CarBookingRepo;
import com.rental.demo.repository.CarRepo;
import com.rental.demo.repository.UserRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

@Service
public class AdminService {

    private final UserRepo userRepo;
    private final CarRepo carRepo;
	private final EmailService emailService;
    private final EntityMapper entityMapper;
    private final CarBookingRepo bookingRepo;

    @Value("${app.images.dir}")
    private String DIR;

    public AdminService(UserRepo userRepo, CarRepo carRepo, EmailService emailService, EntityMapper entityMapper, CarBookingRepo bookingRepo) {
        this.userRepo = userRepo;
        this.carRepo = carRepo;
        this.emailService = emailService;
        this.entityMapper = entityMapper;
        this.bookingRepo = bookingRepo;
    }

    public long getTotalUsers() {
        return userRepo.count();
    }

    public long getTotalCars() {
        return carRepo.count();
    }

    public long getTotalBookings() {
        return bookingRepo.count();
    }
    
	public long getAvailableCars() {
		return carRepo.countByStatus("AVAILABLE");
	}

    public List<CreateUserCmd> getAllUsers() {
        return userRepo.findAll().stream()
                .map(entityMapper::toCreateUserCmd)
                .collect(Collectors.toList());
    }
    
    public List<CarBooking> getBookings(String sortByAmount, String sortByStatus) {
        Sort sort = Sort.unsorted();

        if (sortByAmount != null) {
            if ("asc".equalsIgnoreCase(sortByAmount))
                sort = Sort.by(Sort.Order.asc("amountDue"));
            else if ("desc".equalsIgnoreCase(sortByAmount))
                sort = Sort.by(Sort.Order.desc("amountDue"));
        }

        if (sortByStatus != null) {
            if ("pending".equalsIgnoreCase(sortByStatus))
                return bookingRepo.findByPaymentStatus("pending", sort);
            else if ("completed".equalsIgnoreCase(sortByStatus))
                return bookingRepo.findByPaymentStatus("completed", sort);
        }

        return bookingRepo.findAll(sort);
    }

    public List<Car> getAllCars() {
        return carRepo.findAll();
    }

    public void deleteUser(Long userId) {
        if (userRepo.existsById(userId)) {
            userRepo.deleteById(userId);
            System.out.println("User with ID " + userId + " deleted.");
        } else {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
    }

    public Car addCar(Car car, MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {

            Path uploadDir = Paths.get(DIR, "images", "cars");
            Files.createDirectories(uploadDir);

            String fileName = System.currentTimeMillis() + "-" +
                    StringUtils.cleanPath(image.getOriginalFilename());

            Path filePath = uploadDir.resolve(fileName);
            image.transferTo(filePath);

            car.setImageUrl("/images/cars/" + fileName);
        }

        return carRepo.save(car);
    }

    public void deleteCar(Long carId) {
        if (carRepo.existsById(carId)) {
            carRepo.deleteById(carId);
        } else {
            throw new IllegalArgumentException("Car not found with ID: " + carId);
        }
    }

    public void updateCar(long id, Car car, MultipartFile image) throws IOException{
        Car existingCar = carRepo.findById(id);
        if(existingCar == null)
            return;

        if (image != null && !image.isEmpty()) {

            Path uploadDir = Paths.get(DIR, "images", "cars");
            Files.createDirectories(uploadDir);

            String fileName = System.currentTimeMillis() + "-" +
                    StringUtils.cleanPath(image.getOriginalFilename());

            Path filePath = uploadDir.resolve(fileName);
            image.transferTo(filePath);

            existingCar.setImageUrl("/images/cars/" + fileName);
        }

        existingCar.setModel(car.getModel());
        existingCar.setType(car.getType());
        existingCar.setPrice(car.getPrice());
        existingCar.setStatus(car.getStatus());

        carRepo.save(existingCar);
    }
    
    public void finishPayment(Long bookingId) {
        CarBooking booking = bookingRepo.findById(bookingId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid booking ID: " + bookingId));
        booking.setPaymentStatus("COMPLETED");
        emailService.sendPaymentFinishedEmail(booking);
        bookingRepo.save(booking);
    }

    public Car getCarById(long id) {
        return carRepo.findById(id);
    }

	public CreateUserCmd getUserById(long id) {
		return userRepo.findById(id)
                .map(entityMapper::toCreateUserCmd)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }
}
