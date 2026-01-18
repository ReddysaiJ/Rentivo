package com.rental.demo.service;

import com.rental.demo.model.Car;
import com.rental.demo.model.CarBooking;
import com.rental.demo.model.CarBookingDTO;
import com.rental.demo.model.User;
import com.rental.demo.repository.CarBookingRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CarBookingService {

    private CarBookingRepo bookingRepo;
    private CarService carService;
    private EntityMapper entityMapper;
    private EmailService emailService;

    public void createBooking(Long carId, LocalDate start, LocalDate end, User user) {
        Car car = carService.getCarById(carId);
        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(start, end);
        CarBooking booking = new CarBooking();
        booking.setCar(car);
        booking.setCustomer(user);
        booking.setStartDate(start);
        booking.setEndDate(end);
        booking.setAmountDue(daysBetween * car.getPrice());
        bookingRepo.save(booking);
        emailService.sendNewBookingEmail(booking);
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


    public CarBookingDTO getBooking(Long id) {
        return bookingRepo.findById(id)
                .map(entityMapper::toCarBookingDTO)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public void updateBooking(CarBookingDTO carBookingDTO, LocalDate startDate, LocalDate endDate) {
        CarBooking existingBooking = bookingRepo.findById(carBookingDTO.id())
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        existingBooking.setStartDate(startDate);
        existingBooking.setEndDate(endDate);
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        double amountDue = daysBetween * existingBooking.getCar().getPrice();
        existingBooking.setAmountDue(amountDue);
    	emailService.sendBookingUpdateEmail(existingBooking);
        bookingRepo.save(existingBooking);
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

    public List<CarBookingDTO> getBookingsByCustomerUsername(String username) {
        List<CarBookingDTO> bookings = new ArrayList<>();
        var carBookings = bookingRepo.findByCustomerUsername(username);
        for(CarBooking carBooking : carBookings) {
            LocalDateTime bookingStart = carBooking.getStartDate().atStartOfDay();
            Boolean update = !bookingStart.isBefore(LocalDateTime.now().plusHours(3));

            bookings.add(new CarBookingDTO(
                    carBooking.getId(),
                    carBooking.getCustomer(),
                    carBooking.getCar(),
                    carBooking.getStartDate(),
                    carBooking.getEndDate(),
                    carBooking.getAmountDue(),
                    update,
                    carBooking.getPaymentStatus()
            ));
        }
        return bookings;
    }

    public Iterable<CarBooking> getAllBookings() {
        return bookingRepo.findAll();
    }

}
