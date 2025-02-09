package com.rental.demo.controller;

import com.rental.demo.model.Car;
import com.rental.demo.model.CarBooking;
import com.rental.demo.model.User;
import com.rental.demo.model.UserDetailsImpl;
import com.rental.demo.service.CarBookingService;
import com.rental.demo.service.CarService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/booking")
public class CarBookingController {

    @Autowired
    private CarBookingService bookingService;

    @Autowired
    private CarService carService;

    @GetMapping("/create")
    public String showBookingForm(@RequestParam("carId") Long carId, @RequestParam("startDate") String startDate, 
    							@RequestParam("endDate") String endDate, Model model) {
        Car car = carService.getCarById(carId);
        model.addAttribute("car", car);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(start, end);

        double amountDue = daysBetween * car.getPrice();
        model.addAttribute("amountDue", amountDue);

        model.addAttribute("carBooking", new CarBooking());
        return "create-booking";
    }

    @PostMapping("/create")
    public String createBooking(@ModelAttribute("carBooking") CarBooking carBooking, @RequestParam("car.id") Long carId, @RequestParam("startDate") String startDate,
                                @RequestParam("endDate") String endDate, @RequestParam("amountDue") double amountDue,
                                @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        Car car = carService.getCarById(carId);
        carBooking.setCar(car);
        User user = userDetails.getUser();
        carBooking.setCustomer(user);

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        carBooking.setStartDate(start);
        carBooking.setEndDate(end);

        carBooking.setAmountDue(amountDue);

        bookingService.createBooking(carBooking);
        return "redirect:/booking/myBookings";
    }

    @GetMapping("/myBookings")
    public String viewBookings(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUsername();
        List<CarBooking> bookings = bookingService.getBookingsByCustomerUsername(username);

        LocalDateTime now = LocalDateTime.now();

        for (CarBooking booking : bookings) {
            LocalDateTime bookingStart = booking.getStartDate().atStartOfDay();
            if (bookingStart.isBefore(now.plusHours(3))) {
                booking.setCancelableOrUpdatable(false);
            } else {
                booking.setCancelableOrUpdatable(true);
            }
        }

        model.addAttribute("bookings", bookings);
        return "my-bookings";
    }

    @PostMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable Long id) {
        boolean isCancelled = bookingService.cancelBooking(id);
        
        if (isCancelled)
            return "redirect:/booking/myBookings?message=Booking cancelled successfully.";
        else
            return "redirect:/booking/myBookings?error=Error occurred while canceling the booking.";
    }


    @GetMapping("/view/{id}")
    public String viewBookingDetails(@PathVariable Long id, Model model) {
        model.addAttribute("booking", bookingService.getBooking(id));
        return "view-booking";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        CarBooking booking = bookingService.getBooking(id);
        model.addAttribute("carBooking", booking);
        return "update-booking";
    }

    @PostMapping("/update/{id}")
    public String updateBooking(@PathVariable Long id, 
                                @ModelAttribute("carBooking") CarBooking carBooking) {
        CarBooking existingBooking = bookingService.getBooking(id);

        LocalDate startDate = carBooking.getStartDate();
        LocalDate endDate = carBooking.getEndDate();

        if (endDate.isBefore(startDate)) {
            return "redirect:/booking/update/" + id + "?error=InvalidDate";
        }

        boolean available = bookingService.isCarAvailable(existingBooking.getCar().getId(), startDate, endDate, existingBooking.getId());
        if (!available) {
            return "redirect:/booking/update/" + id + "?error=CarNotAvailable";
        }

        existingBooking.setStartDate(startDate);
        existingBooking.setEndDate(endDate);
        
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        double amountDue = daysBetween * existingBooking.getCar().getPrice();
        existingBooking.setAmountDue(amountDue);

        bookingService.updateBooking(existingBooking);
        return "redirect:/booking/myBookings";
    }

    @GetMapping("/checkCarAvailability")
    @ResponseBody
    public ResponseEntity<?> checkCarAvailability(@RequestParam Long carId, @RequestParam String startDate, @RequestParam String endDate,
                                                  @RequestParam(required = false) Long excludeBookingId) {
        boolean available = bookingService.isCarAvailable(carId, LocalDate.parse(startDate), LocalDate.parse(endDate), excludeBookingId);
        return ResponseEntity.ok().body("{\"available\": " + available + "}");
    }
}
