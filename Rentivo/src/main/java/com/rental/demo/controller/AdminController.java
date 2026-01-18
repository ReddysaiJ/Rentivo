package com.rental.demo.controller;

import com.rental.demo.model.*;
import com.rental.demo.service.AdminService;

import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", adminService.getTotalUsers());
        model.addAttribute("totalCars", adminService.getTotalCars());
        model.addAttribute("totalBookings", adminService.getTotalBookings());
        model.addAttribute("availableCars", adminService.getAvailableCars());
        
        return "adminDashboard";
    }

    @GetMapping("/manageCars")
    public String manageCars(Model model) {
        model.addAttribute("cars", adminService.getAllCars());
        return "manageCars";
    }

    @GetMapping("/manageUsers")
    public String manageUsers(@RequestParam(value = "editId", required = false) Long editId, Model model) {
        if (editId != null) {
            CreateUserCmd userToEdit = adminService.getUserById(editId);
            model.addAttribute("userToEdit", userToEdit);
        }
        model.addAttribute("users", adminService.getAllUsers());
        return "manageUsers";
    }

    @GetMapping("/addCar")
    public String showAddCarForm(Model model) {
        model.addAttribute("car", new Car());
        return "addCar";
    }

    @PostMapping("/addCar")
    public String addCar(@Valid @ModelAttribute Car car,
                         BindingResult result,
                         @RequestParam("image") MultipartFile image,
                         RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Invalid car data.");
            return "redirect:/admin/manageCars";
        }
        try {
            adminService.addCar(car, image);
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Image upload failed.");
            return "redirect:/admin/manageCars";
        }

        redirectAttributes.addFlashAttribute("message", "Car added successfully.");
        return "redirect:/admin/manageCars";
    }
    
    @GetMapping("/car/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Car car = adminService.getCarById(id);
        model.addAttribute("car", car);
        return "updateCar"; 
    }

    @PostMapping("/car/update/{id}")
    public String updateCar(@PathVariable("id") long id, @Valid @ModelAttribute Car car, 
                            @RequestParam(value = "image", required = false) MultipartFile image,
                            BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Invalid car data.");
            return "redirect:/admin/manageCars";
        }

        try {
            adminService.updateCar(id, car, image);
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Image upload failed: " + e.getMessage());
            return "redirect:/admin/manageCars";
        }

        redirectAttributes.addFlashAttribute("message", "Car updated successfully.");
        return "redirect:/admin/manageCars";
    }

    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteUser(id);
            redirectAttributes.addFlashAttribute("message", "User deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting user: " + e.getMessage());
        }
        return "redirect:/admin/manageUsers";
    }

    @PostMapping("/car/delete/{id}")
    public String deleteCar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteCar(id);
            redirectAttributes.addFlashAttribute("message", "Car deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting car: " + e.getMessage());
        }
        return "redirect:/admin/manageCars";
    }
    
    @GetMapping("/manageBookings")
    public String manageBookings(@RequestParam(required = false) String sortByAmount,
                                 @RequestParam(required = false) String sortByStatus,
                                 Model model) {
        List<CarBooking> bookings = adminService.getBookings(sortByAmount, sortByStatus);
        model.addAttribute("bookings", bookings);
        model.addAttribute("sortByAmount", sortByAmount);
        model.addAttribute("sortByStatus", sortByStatus);
        return "manageBookings";
    }

    @PostMapping("/payment/finish/{id}")
    public String finishPayment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            adminService.finishPayment(id);
            redirectAttributes.addFlashAttribute("message", "Booking payment marked as finished.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating payment: " + e.getMessage());
        }
        return "redirect:/admin/manageBookings";
    }
}
