package com.nuvepro.coworkspacebooking.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10)
    private int roomId;

    @Column(length = 10,nullable = false)
    private int roomNumber;

    @Column(nullable = false)
    private boolean available;

    @Column(length = 20,nullable = false)
    private String roomType;

    @Column(length = 20,nullable = false)
    private int floor;

    @Column(length = 10,nullable = false)
    private int roomSeatingCapacity;

    @Column(length=10,nullable = false)
    private int roomRentPerHour;

    @Column(length = 10,nullable = false)
    private int roomRentByDay;

    @ManyToOne
    @JoinColumn(name = "workspaceId_fk")
    @JsonBackReference
    private CoWorkspace workspace;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getRoomSeatingCapacity() {
        return roomSeatingCapacity;
    }

    public void setRoomSeatingCapacity(int roomSeatingCapacity) {
        this.roomSeatingCapacity = roomSeatingCapacity;
    }

    public CoWorkspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(CoWorkspace workspace) {
        this.workspace = workspace;
    }




//    @OneToOne
//    @JoinColumn(name = "roomId_fk",referencedColumnName = "roomId")
//    @JsonBackReference
//    private WorkspaceDetails RoomId;

    public Room() {
    }

    public Room(String roomType, int roomRentPerHour, int roomRentByDay) {
        this.roomType = roomType;
        this.roomRentPerHour = roomRentPerHour;
        this.roomRentByDay = roomRentByDay;
    }



    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getRoomRentPerHour() {
        return roomRentPerHour;
    }

    public void setRoomRentPerHour(int roomRentPerHour) {
        this.roomRentPerHour = roomRentPerHour;
    }

    public int getRoomRentByDay() {
        return roomRentByDay;
    }

    public void setRoomRentByDay(int roomRentByDay) {
        this.roomRentByDay = roomRentByDay;
    }
}
