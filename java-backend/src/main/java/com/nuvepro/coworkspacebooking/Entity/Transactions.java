package com.nuvepro.coworkspacebooking.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Transactions {

    @Id
    private int transactionId;

    private int customerId;

    private int bookingId;

    private Date paymentDate;

    private int paymentAmount;

    private String status;

}
