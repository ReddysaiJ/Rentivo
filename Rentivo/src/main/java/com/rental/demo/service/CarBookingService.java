package com.rental.demo.service;

import com.rental.demo.model.CarBooking;
import com.rental.demo.repository.CarBookingRepo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarBookingService {

    @Autowired
    private CarBookingRepo bookingRepo;

    @Autowired
    private EmailService emailService;

    public CarBooking createBooking(CarBooking booking) {
        CarBooking savedBooking = bookingRepo.save(booking);
        emailService.sendNewBookingEmail(booking);
        return savedBooking;
    }

    public boolean cancelBooking(Long bookingId) {
        CarBooking booking = bookingRepo.findById(bookingId).orElse(null);
        if (booking != null) {
            bookingRepo.deleteById(bookingId);
            emailService.sendBookingCancellationEmail(booking);
            return true;
        }
        return false;
    }


    public CarBooking getBooking(Long id) {
        return bookingRepo.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public void updateBooking(CarBooking booking) {
    	emailService.sendBookingUpdateEmail(booking);
        bookingRepo.save(booking);
    }

    public boolean isCarAvailable(Long carId, LocalDate startDate, LocalDate endDate, Long excludeBookingId) {
        List<CarBooking> overlappingBookings;
        if (excludeBookingId != null) {
            overlappingBookings = bookingRepo.findOverlappingBookingsExcluding(carId, startDate, endDate, excludeBookingId);
        } else {
            overlappingBookings = bookingRepo.findOverlappingBookings(carId, startDate, endDate);
        }
        return overlappingBookings.isEmpty();
    }

    public List<CarBooking> getBookingsByCustomerUsername(String username) {
        return bookingRepo.findByCustomerUsername(username);
    }

    public Iterable<CarBooking> getAllBookings() {
        return bookingRepo.findAll();
    }
}
