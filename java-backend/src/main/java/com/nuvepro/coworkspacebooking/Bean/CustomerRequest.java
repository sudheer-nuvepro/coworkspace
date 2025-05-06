package com.nuvepro.coworkspacebooking.Bean;

public class CustomerRequest {

    private String customerEmail;

    private String customerFirstName;

    private String customerLastName;

    private String companyAddress;

    private String password;

    private int pincode;

    private String phoneNumber;

    private String city;

    private String state;

    private String country;

    private String companyName;

    private String title;

    public CustomerRequest(){
    }

    public CustomerRequest(String customerEmail,
                           String customerFirstName, String getCustomerLastName, String companyAddress,
                           int pincode, String phoneNumber, String city, String state, String country,
                           String companyName, String title) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
