package com.nuvepro.coworkspacebooking.paymentTask;

import com.nuvepro.coworkspacebooking.Entity.Booking;
import com.nuvepro.coworkspacebooking.Repository.BookingRepository;
import com.nuvepro.coworkspacebooking.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;

public class PaymentTask implements Runnable {

    @Autowired
    BookingRepository bookingRepository;

    private Booking booking;

    @Autowired
    BookingService bookingService;

    public PaymentTask(Booking booking) {
            this.booking = booking;
    }

//  Method to perform the payment check
    public void run() {
        System.out.println("Inside payment task Class to check whether paymnent is complete");
        if (!(booking.getPaymentStatus().equals("Succeeded"))) {
           bookingService.updateRoomToAvailableAsPaymentUnsuccessful(booking);
        }

    }
}


