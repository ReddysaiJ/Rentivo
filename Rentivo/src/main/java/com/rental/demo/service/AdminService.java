package com.rental.demo.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rental.demo.exception.UserNotFoundException;
import com.rental.demo.model.Car;
import com.rental.demo.model.CarBooking;
import com.rental.demo.model.User;
import com.rental.demo.repository.CarBookingRepo;
import com.rental.demo.repository.CarRepo;
import com.rental.demo.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final CarRepo carRepo;
    private final CarBookingRepo bookingRepo;

    // Get total count of users
    public long getTotalUsers() {
        return userRepo.count();
    }

    // Get total count of cars
    public long getTotalCars() {
        return carRepo.count();
    }

    // Get total count of bookings
    public long getTotalBookings() {
        return bookingRepo.count();
    }
    
	public long getAvailableCars() {
		return carRepo.countByStatus("AVAILABLE");
	}

    // Retrieve all users
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
    
    public List<CarBooking> getBookings(String sortByAmount, String sortByStatus) {
        Sort sort = Sort.unsorted();

        if (sortByAmount != null) {
            if ("asc".equalsIgnoreCase(sortByAmount)) {
                sort = Sort.by(Sort.Order.asc("amountDue"));
            } else if ("desc".equalsIgnoreCase(sortByAmount)) {
                sort = Sort.by(Sort.Order.desc("amountDue"));
            }
        }

        if (sortByStatus != null) {
            if ("pending".equalsIgnoreCase(sortByStatus)) {
                return bookingRepo.findByPaymentStatus("pending", sort);
            } else if ("completed".equalsIgnoreCase(sortByStatus)) {
                return bookingRepo.findByPaymentStatus("completed", sort);
            }
        }

        return bookingRepo.findAll(sort);
    }


    // Retrieve all cars
    public List<Car> getAllCars() {
        return carRepo.findAll();
    }

    // Delete a user by ID
    public void deleteUser(Long userId) {
        if (userRepo.existsById(userId)) {
            userRepo.deleteById(userId);
            System.out.println("User with ID " + userId + " deleted.");
        } else {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
    }

    // Add a new car
    public Car addCar(Car car) {
        return carRepo.save(car);
    }

    // Delete a car by ID
    public void deleteCar(Long carId) {
        if (carRepo.existsById(carId)) {
            carRepo.deleteById(carId);
        } else {
            throw new IllegalArgumentException("Car not found with ID: " + carId);
        }
    }

    public void updateCar(long id, Car car) {
        Optional<Car> existingCar = Optional.of(carRepo.findById(id));
        if (existingCar.isPresent()) {
            Car updatedCar = existingCar.get();
            updatedCar.setModel(car.getModel());
            updatedCar.setType(car.getType());
            updatedCar.setPrice(car.getPrice());
            updatedCar.setStatus(car.getStatus());
            updatedCar.setImageUrl(car.getImageUrl());  // Set the image URL if updated
            carRepo.save(updatedCar);
        }
    }
    
    public void finishPayment(Long bookingId) {
        CarBooking booking = bookingRepo.findById(bookingId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid booking ID: " + bookingId));
        booking.setPaymentStatus("COMPLETED");
        bookingRepo.save(booking);
    }


    public Car getCarById(long id) {
        return carRepo.findById(id);
    }

	public User getUserById(long id) {
		return userRepo.findById(id);
	}
}
