package com.rental.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.rental.demo.model.Car;
import com.rental.demo.service.CarService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/list")
    public String listCars(@RequestParam(required = false) String startDate,
                           @RequestParam(required = false) String endDate,
                           Model model) {
        List<Car> cars = new ArrayList<>();

        if (startDate != null && endDate != null) {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            cars = carService.getAvailableCars(start, end);
        }

        model.addAttribute("cars", cars);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "carList";
    }
}
