package com.nuvepro.coworkspacebooking.Entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;


@Entity
public class CabBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10)
    private int travelBookingId;

    @Column(length = 100)
    private String pickUpLocation;

    @Column(length = 100)
    private String Destination;

    private Date pickUpDate;

    private Timestamp pickUpTime;
    private float amount;

    private int customerId;

    private int numberOfPeople;

    private int distanceFromCoworkSpace;

    private String status;

    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public int getTravelBookingId() {
        return travelBookingId;
    }

    public void setTravelBookingId(int travelBookingId) {
        this.travelBookingId = travelBookingId;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public Timestamp getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(Timestamp pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public int getDistanceFromCoworkSpace() {
        return distanceFromCoworkSpace;
    }

    public void setDistanceFromCoworkSpace(int distanceFromCoworkSpace) {
        this.distanceFromCoworkSpace = distanceFromCoworkSpace;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}