package com.nuvepro.coworkspacebooking.Bean;

import java.time.LocalDateTime;
import java.util.Date;

public class BookingRequest {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean travelOpted;
    private String paymentType;
    private Date paymentDate;
    private Date bookingDate;

    public boolean isTravelOpted() {
        return travelOpted;
    }

    public void setTravelOpted(boolean travelOpted) {
        this.travelOpted = travelOpted;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
