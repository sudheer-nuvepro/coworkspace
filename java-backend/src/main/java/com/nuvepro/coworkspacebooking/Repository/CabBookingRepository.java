package com.nuvepro.coworkspacebooking.Repository;

import com.nuvepro.coworkspacebooking.Entity.CabBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CabBookingRepository extends JpaRepository<CabBooking,Integer> {
    CabBooking findByTravelBookingId(int travelBookingId);

    List<CabBooking> findByCustomerId(int customerId);



}
