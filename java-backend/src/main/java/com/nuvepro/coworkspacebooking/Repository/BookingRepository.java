package com.nuvepro.coworkspacebooking.Repository;

import com.nuvepro.coworkspacebooking.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Booking findByBookingId(int bookingId);
    boolean existsByBookingId(int bookingId);

    List<Booking> findByRoomIdAndCheckOutDateGreaterThanEqualAndCheckInDateLessThanEqual(int roomId, LocalDateTime checkOutDate, LocalDateTime checkInDate);


}
