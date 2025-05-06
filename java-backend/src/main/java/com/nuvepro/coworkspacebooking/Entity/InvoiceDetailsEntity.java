package com.nuvepro.coworkspacebooking.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity
public class InvoiceDetailsEntity {

    @Id
    private int invoiceNo;

    private int bookingId;

    private int total;

    private int workspaceId;

    private Timestamp fromDate;

    private Timestamp toDate;

    private String customerFirstName;

    private String customerLastName;

    private String companyName;

    private int workspaceAmount;

    private int cabAmount;

    private int totalAmount;


}
