package com.rental.demo.service;

import com.rental.demo.model.CarDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rental.demo.model.Car;
import com.rental.demo.repository.CarRepo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

@Service
public class CarService {

    private final CarRepo carRepo;
    private final EntityMapper entityMapper;

    public CarService(CarRepo carRepo, EntityMapper entityMapper) {
        this.carRepo = carRepo;
        this.entityMapper = entityMapper;
    }

    public List<Car> getAllCars() {
        return carRepo.findAll();
    }
    
    public CarDTO getCarDtoById(Long carId) {
        return carRepo.findById(carId)
                .map(entityMapper::toCarDTO)
                .orElseThrow(() -> new RuntimeException("Car not found"));
    }

    public Car getCarById(Long carId) {
        return carRepo.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));
    }
    
    public List<CarDTO> getAvailableCars(LocalDate startDate, LocalDate endDate) {
        return carRepo.findAvailableCars(startDate, endDate)
                .stream().map(entityMapper::toCarDTO)
                .toList();
    }

    public long getCarsCount() {
        return carRepo.count();
    }

//    public void addCar(String model, String type, Double price, MultipartFile image) throws IOException {
//        String imageUrl = saveImage(image);
//
//        Car car = new Car();
//        car.setModel(model);
//        car.setType(type);
//        car.setPrice(price);
//        car.setImageUrl(imageUrl);
//
//        carRepo.save(car);
//    }
//
//    private String saveImage(MultipartFile image) throws IOException {
//        if (image == null || image.isEmpty())
//            return null;
//
//        File directory = new File(IMAGE_UPLOAD_DIR);
//        if (!directory.exists()) {
//            directory.mkdirs();
//        }
//
//        String imageName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
//        Path imagePath = Paths.get(IMAGE_UPLOAD_DIR + imageName);
//        Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
//
//        return "/images/cars/" + imageName;
//    }

    public void deleteCar(Long id) {
        carRepo.deleteById(id);
    }
}