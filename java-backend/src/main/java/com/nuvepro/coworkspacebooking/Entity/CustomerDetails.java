
package com.nuvepro.coworkspacebooking.Entity;


import jakarta.persistence.*;

@Entity
public class CustomerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length=10)
    private int customerId;

    @Column(length = 30,nullable = false)
    private String customerEmail;

    @Column(length = 10,nullable = false)
    private String customerFirstName;

    @Column(length = 10,nullable = false)
    private String customerLastName;

    @Column(length = 20,nullable = false)
    private String companyAddress;

    @Column(length = 10,nullable = false)
    private int pincode;

    @Column(length = 20,nullable = false)
    private String phoneNumber;

    @Column(length = 20,nullable = false)
    private String city;

    @Column(length = 20,nullable = false)
    private String state;

    @Column(length = 20,nullable = false)
    private String country;

    @Column(length = 20,nullable = false)
    private String companyName;

    @Column(length = 25,nullable = false)
    private String title;



    public CustomerDetails(){

    }

    public CustomerDetails(int customerId, String customerEmail,
                           String customerFirstName, String getCustomerLastName, String companyAddress,
                           int pincode, String phoneNumber, String city, String state, String country,
                           String companyName, String title) {
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.customerFirstName = customerFirstName;
        this.customerLastName = getCustomerLastName;
        this.companyAddress = companyAddress;
        this.pincode = pincode;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.state = state;
        this.country = country;
        this.companyName = companyName;
        this.title = title;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }




}
