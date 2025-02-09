package com.rental.demo.repository;

import com.rental.demo.model.CarBooking;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarBookingRepo extends JpaRepository<CarBooking, Long> {
    List<CarBooking> findByCustomerUsername(String username);
    List<CarBooking> findTop5ByCustomerIdOrderByStartDateDesc(Long customerId);
    List<CarBooking> findByPaymentStatus(String paymentStatus, Sort sort);
    
    @Query("SELECT b FROM CarBooking b WHERE b.car.id = :carId " + "AND ((b.startDate BETWEEN :startDate AND :endDate) " +
            "OR (b.endDate BETWEEN :startDate AND :endDate))")
     List<CarBooking> findOverlappingBookings(Long carId, LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT b FROM CarBooking b WHERE b.car.id = :carId AND (b.startDate <= :endDate AND b.endDate >= :startDate) AND b.id != :excludeBookingId")
    List<CarBooking> findOverlappingBookingsExcluding(@Param("carId") Long carId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("excludeBookingId") Long excludeBookingId);
}
