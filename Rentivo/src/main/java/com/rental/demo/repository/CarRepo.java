package com.rental.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rental.demo.model.Car;

@Repository
public interface CarRepo extends JpaRepository<Car, Long>{
	@Query("SELECT c FROM Car c WHERE c.id NOT IN " +
		       "(SELECT cb.car.id FROM CarBooking cb WHERE " +
		       "cb.startDate <= :endDate AND cb.endDate >= :startDate)")
	List<Car> findAvailableCars(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	Car findById(long id);
	long countByStatus(String string);
}
