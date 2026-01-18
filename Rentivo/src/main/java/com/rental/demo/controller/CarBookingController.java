package com.rental.demo.controller;

import com.rental.demo.model.*;
import com.rental.demo.service.CarBookingService;
import com.rental.demo.service.CarService;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/booking")
public class CarBookingController {

    private CarBookingService bookingService;
    private CarService carService;

    @GetMapping("/create")
    public String showBookingForm(@RequestParam("carId") Long carId, @RequestParam("startDate") String startDate, 
    							@RequestParam("endDate") String endDate, Model model) {
        CarDTO carDto = carService.getCarDtoById(carId);
        model.addAttribute("car", carDto);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(start, end);

        double amountDue = daysBetween * carDto.price();
        model.addAttribute("amountDue", amountDue);
        model.addAttribute("carBooking", new CarBookingDTO(null, null, null, null, null, null, false, ""));
        return "create-booking";
    }

    @PostMapping("/create")
    public String createBooking(@Valid @ModelAttribute("carBooking") RequestCarBooking requestCarBooking, @RequestParam("car.id") Long carId,
                                @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
                                @RequestParam("amountDue") double amountDue,
                                @AuthenticationPrincipal UserDetailsImpl userDetails,
                                Model model) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        boolean available = bookingService.isCarAvailable(carId, start, end, null);
        if (!available)
            return "redirect:/booking/create?error=CarNotAvailable&carId=" + carId + "&startDate=" + startDate + "&endDate=" + endDate;

        bookingService.createBooking(carId, start, end, userDetails.getUser());
        return "redirect:/booking/myBookings";
    }

    @GetMapping("/myBookings")
    public String viewBookings(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUsername();
        List<CarBookingDTO> bookings = bookingService.getBookingsByCustomerUsername(username);

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
        CarBookingDTO booking = bookingService.getBooking(id);
        System.out.println(booking);
        model.addAttribute("carBooking", booking);
        model.addAttribute("today", LocalDate.now());
        return "update-booking";
    }

    @PostMapping("/update/{id}")
    public String updateBooking(@PathVariable Long id, 
                                @ModelAttribute("carBooking") CarBookingDTO carBookingDto) {
        LocalDate startDate = carBookingDto.startDate();
        LocalDate endDate = carBookingDto.endDate();
        CarBookingDTO booking = bookingService.getBooking(id);

        if (endDate.isBefore(startDate))
            return "redirect:/booking/update/" + id + "?error=InvalidDate";

        boolean available = bookingService.isCarAvailable(booking.car().getId(), startDate, endDate, booking.id());
        if (!available)
            return "redirect:/booking/update/" + id + "?error=CarNotAvailable";

        bookingService.updateBooking(carBookingDto, startDate, endDate);
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
