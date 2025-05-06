package com.nuvepro.coworkspacebooking.Service;

import com.nuvepro.coworkspacebooking.Controller.BookingController;
import com.nuvepro.coworkspacebooking.Entity.Booking;
import com.nuvepro.coworkspacebooking.Entity.CoWorkspace;
import com.nuvepro.coworkspacebooking.Entity.CustomerDetails;
import com.nuvepro.coworkspacebooking.Repository.BookingRepository;
import com.nuvepro.coworkspacebooking.Repository.CoWorkspaceRepository;
import com.nuvepro.coworkspacebooking.Repository.RoomRepository;
import com.nuvepro.coworkspacebooking.Bean.CabBookingRequest;
import com.nuvepro.coworkspacebooking.Entity.*;
import com.nuvepro.coworkspacebooking.Repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;

@Service
public class BookingService {

    private static final Logger logger= LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CoWorkspaceRepository coWorkspaceRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CabBookingRepository cabBookingRepository;

    public List<Room> getAvailableRooms(LocalDateTime startTime, LocalDateTime endTime) {
        long daysBetween = DAYS.between(startTime, endTime);
        long hoursBetween = HOURS.between(startTime,endTime);

        logger.info("startTime : " + startTime + " endTime : " + endTime + " \n daysbetween: "
                + daysBetween +" hoursBetween: " + hoursBetween);

        List<Room> rooms = roomRepository.findByAvailableTrue();
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            int roomNumber=room.getRoomNumber();
            List<Booking> bookings =
                    bookingRepository.findByRoomIdAndCheckOutDateGreaterThanEqualAndCheckInDateLessThanEqual(roomNumber,startTime,endTime);
            if (bookings.isEmpty()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public List<Room> getAvailableRoomsByWorkspaceId(int coWorkspaceId,LocalDateTime startTime, LocalDateTime endTime) {
        CoWorkspace coworkspace=coWorkspaceRepository.findByWorkspaceId(coWorkspaceId);
        List<Room> rooms = roomRepository.findByWorkspaceAndAvailableTrue(coworkspace);
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            int roomNumber=room.getRoomNumber();
            List<Booking> bookings = bookingRepository.findByRoomIdAndCheckOutDateGreaterThanEqualAndCheckInDateLessThanEqual(roomNumber,startTime,endTime);
            if (bookings.isEmpty()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }
    public boolean checkRoomAvailabilityForGivenDate(Room room, LocalDateTime startTime, LocalDateTime endTime){

        List<Booking> bookings=bookingRepository.findByRoomIdAndCheckOutDateGreaterThanEqualAndCheckInDateLessThanEqual(room.getRoomId(),
                startTime,endTime);
        if(!bookings.isEmpty()){
            return false;
        }
        return true;
    }


    public boolean checkIfRoomIsAvailableForBooking(int roomId){
        Room room = roomRepository.findByRoomIdAndAvailableTrue(roomId);

        if(room != null)
            return true;
        else
            return false;
    }

    public Booking updateBookingBeforePayment(Booking booking, Room room){
        booking.setPaymentStatus("Pending");
        booking.setBookingStatus("Pending");
        booking.setRoomId(room.getRoomId());
        booking.setWorkspaceId(room.getWorkspace().getWorkspaceId());


        bookingRepository.save(booking);
        room.setAvailable(false);
        roomRepository.save(room);

        return booking;
    }

    public long calculatePaymentAmountForBooking(Booking booking, Room rooms ){
        LocalDateTime startTime = booking.getCheckInDate();
        LocalDateTime endTime = booking.getCheckOutDate();

        long amountForRoomTypeByDay= rooms.getRoomRentByDay();
        long amountForRoomTypeByHour= rooms.getRoomRentPerHour();

        long daysBetween = DAYS.between(startTime, endTime);
        long hoursBetween = HOURS.between(startTime,endTime);
        long hours=hoursBetween%24;
        long paymentAmount=(amountForRoomTypeByDay*daysBetween)+(amountForRoomTypeByHour*hours);

        logger.info("startTime : " + startTime + " endTime : " + endTime + " \n daysbetween: "
                + daysBetween + " hoursBetween: " + hoursBetween +" \n totalAmount : "+ paymentAmount);

        return paymentAmount;
    }

    public Booking updateBookingAfterPayment(int bookingId, String paymentStatus) {

        Booking booking;
        try{
            booking=bookingRepository.findByBookingId(bookingId);
        }catch (Exception e){
            return null;
        }

        int roomId = booking.getRoomId();
        Room room = roomRepository .findByRoomId(roomId);
        if(booking!=null && room.isAvailable()==false){
            if("succeeded".equals(paymentStatus)){
                booking.setPaymentStatus("succeeded");
                booking.setBookingStatus("active");
            }
            bookingRepository.save(booking);
        }
        return booking;
    }

    public void updateRoomToAvailableAsPaymentUnsuccessful(Booking booking){
        int roomId = booking.getRoomId();

        Room room = roomRepository.findByRoomId(roomId);

        if(room.isAvailable() == false){
            System.out.println("Setting Room Availability to true as Payment is unsuccessful");
            room.setAvailable(true);
            roomRepository.save(room);
        }
    }
    public void cancelBooking(int id) {
        Booking booking = bookingRepository.findByBookingId(id);
        booking.setBookingStatus("Cancelled");
        bookingRepository.save(booking);
    }

    public CabBooking bookCab(Float amount, CabBookingRequest cabBookingRequest, CustomerDetails customerdetails) {
        CabBooking cabBooking = new CabBooking();

        cabBooking.setPickUpLocation(cabBookingRequest.getPickUpLocation());
        cabBooking.setDestination(cabBookingRequest.getDestination());
        cabBooking.setPickUpDate(cabBookingRequest.getDate());
        cabBooking.setPickUpTime(cabBookingRequest.getTime());
        cabBooking.setCustomerId(customerdetails.getCustomerId());
        cabBooking.setAmount(amount);
        cabBooking.setNumberOfPeople(cabBookingRequest.getNumberOfPeople());
        cabBooking.setDistanceFromCoworkSpace(cabBookingRequest.getDistance());
        cabBooking.setStatus("Active");

        return cabBookingRepository.save(cabBooking);
    }

}
