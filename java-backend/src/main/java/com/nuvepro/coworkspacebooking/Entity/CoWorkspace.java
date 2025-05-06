package com.nuvepro.coworkspacebooking.Entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class CoWorkspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workspaceId",length = 10)
    private Integer workspaceId;


//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "workspaceId_fk",referencedColumnName = "workspaceId")
//    @JsonManagedReference
//    private List<Room> room;


    @Column(length = 100,nullable = false)
    private String coworkingSpaceName;

    @Column(length = 10,nullable = false)
    private int totalCapacity;

    @Column(length = 20,nullable = false)
    private String phoneNumber;

    @Column(length = 50,nullable = false)
    private String companyEmail;

    @Column(length = 200,nullable = false)
    private String address;


    @Column(length = 50,nullable = false)
    private String city;

    @Column(length = 50,nullable = false)
    private String state;

    @Column(nullable = false)
    private boolean isCabFacilityAvailable;

    @Column(length = 10,nullable = false)
    private String pincode;

    @Column(length = 5,nullable = false)
    private float rating;

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setWorkspaceId(Integer workspaceId) {
        this.workspaceId = workspaceId;
    }


//    public List<Room> getRoom() {
//        return room;
//    }
//
//    public void setRoom(List<Room> room) {
//        this.room = room;
//    }


    public int getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(int workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getCoworkingSpaceName() {
        return coworkingSpaceName;
    }

    public void setCoworkingSpaceName(String coworkingSpaceName) {
        this.coworkingSpaceName = coworkingSpaceName;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public boolean isCabFacilityAvailable() {
        return isCabFacilityAvailable;
    }

    public void setCabFacilityAvailable(boolean cabFacilityAvailable) {
        isCabFacilityAvailable = cabFacilityAvailable;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
