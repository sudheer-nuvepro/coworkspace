package com.nuvepro.coworkspacebooking.Controller;

import com.nuvepro.coworkspacebooking.Entity.Booking;
import com.nuvepro.coworkspacebooking.Entity.CustomerDetails;
import com.nuvepro.coworkspacebooking.Repository.*;
import com.nuvepro.coworkspacebooking.Bean.CabBookingRequest;
import com.nuvepro.coworkspacebooking.Entity.*;
import com.nuvepro.coworkspacebooking.Service.BookingService;
import com.nuvepro.coworkspacebooking.paymentTask.PaymentTask;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CustomerRepository customerRepo;

    private static final Logger logger= LoggerFactory.getLogger(BookingController.class);
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CabBookingRepository cabBookingRepository;

    private HashMap<Integer,Float> hm4 = new HashMap<>();
    private HashMap<Integer,Float> hm6 = new HashMap<>();
    private HashMap<Integer,Float> hm8 = new HashMap<>();

    private int base4 = 150, base6 = 200, base8 = 250;


    @Value("${payment.api.url}")
    private String paymentApiUrl; // the URL of the payment API



    @PostMapping("/bookRoom/{customerId}/{roomId}")
    public Object bookRoom(@PathVariable int roomId, @PathVariable int customerId, @RequestBody Booking booking, HttpServletResponse response) throws IOException {

        booking.setCustomerId(customerId);
        Room room = roomRepository.findByRoomId(roomId);
        if (bookingService.checkRoomAvailabilityForGivenDate(room, booking.getCheckInDate(),booking.getCheckOutDate())){
            if(bookingService.checkIfRoomIsAvailableForBooking(roomId)) {
                System.out.println(room.getRoomNumber());
                Booking booking1 = bookingService.updateBookingBeforePayment(booking, room);
                System.out.println("Updated the booking before payment");

                // Schedule the payment check task to run in 10 minutes
                ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                PaymentTask paymentCheckTask = new PaymentTask(booking1);
                executor.schedule(paymentCheckTask, 5, TimeUnit.MINUTES);

                System.out.println("Going to payment");
                long paymentAmount = bookingService.calculatePaymentAmountForBooking(booking, room);
                String paymentUrl = paymentApiUrl + "?amount=" + URLEncoder.encode(String.valueOf(paymentAmount)) +
                        "&bookingId=" + URLEncoder.encode(String.valueOf(booking.getBookingId()), StandardCharsets.UTF_8);
                response.sendRedirect(paymentUrl);
                return paymentUrl;
            }
        }

        return null;
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<?> updateBooking(@RequestParam String PaymentId,
                                           @RequestParam String paymentStatus,
                                           @PathVariable int bookingId){
        Booking updatedBooking=bookingService.updateBookingAfterPayment(bookingId,paymentStatus);

        if (updatedBooking==null){
            return new ResponseEntity<>("Booking not found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedBooking,HttpStatus.OK);
    }

    @GetMapping("/getAllBookings")
    public ResponseEntity getAllBookings(){

        BookingResponse bookingResponse=new BookingResponse();
        List<Booking> bookings=bookingRepository.findAll();

        if(bookings.isEmpty()){
            bookingResponse.setResponseMessage("No records found");
            bookingResponse.setResponseStatus("booking");
            return new ResponseEntity(bookingResponse,HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity(bookings,HttpStatus.OK);
        }

    }

    @DeleteMapping("/cancelBookingById/{id}")
    public ResponseEntity cancelBookingById(@PathVariable int id) {
        BookingResponse bookingResponse=new BookingResponse();
        Booking booking;
        try {
            booking = bookingRepository.findById(id).orElseThrow(() -> new Exception());
        }catch (Exception e){
            bookingResponse.setResponseMessage("Invalid id provided");
            bookingResponse.setResponseStatus("bookingId");
            return new ResponseEntity<>(bookingResponse,HttpStatus.BAD_REQUEST);
        }
        bookingService.cancelBooking(id);
        bookingResponse.setResponseStatus("Success");
        bookingResponse.setResponseMessage("Booking cancelled successfully for Booking Id : "+id);
        return new ResponseEntity(bookingResponse,HttpStatus.OK);

    }

    @PostMapping("/bookCab/{customer_id}")
    public ResponseEntity bookingCab(@RequestBody CabBookingRequest cabBookingRequest, @PathVariable int customer_id){

        CabBookingResponse cabBookingResponse = new CabBookingResponse();
        CustomerDetails customerDetails;
        try{
            customerDetails = customerRepo.findByCustomerId(customer_id);
            int id = customerDetails.getCustomerId();
        }catch (NullPointerException e){
            cabBookingResponse.setResponseMessage("Invalid Customer Id provided");
            cabBookingResponse.setResponseStatus("Failure");
            return new ResponseEntity(cabBookingResponse,HttpStatus.BAD_REQUEST);
        }
        CabBooking cabBooking;
        if(cabBookingRequest.getNumberOfPeople() <= 4) {
            addPrice(base4, hm4, 17);
            cabBooking = bookingService.bookCab(hm4.get(cabBookingRequest.getDistance()),cabBookingRequest,customerDetails);
        }
        else if(cabBookingRequest.getNumberOfPeople() <= 6) {
            addPrice(base6, hm6, 20);
            cabBooking = bookingService.bookCab(hm6.get(cabBookingRequest.getDistance()),cabBookingRequest,customerDetails);
        }
        else if(cabBookingRequest.getNumberOfPeople() <= 8) {
            addPrice(base8, hm8, 22);
            cabBooking = bookingService.bookCab(hm8.get(cabBookingRequest.getDistance()),cabBookingRequest,customerDetails);
        }
        else {
            cabBookingResponse.setResponseMessage("Invalid number of people");
            cabBookingResponse.setResponseStatus("Failure");
            return new ResponseEntity(cabBookingResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(cabBooking,HttpStatus.OK);
    }

    @GetMapping("/getCabRent")
    public ResponseEntity getCabRent(@RequestParam int numberOfPeople, @RequestParam int distance) {

        if (numberOfPeople <= 0)
            return new ResponseEntity("Invalid number of passengers", HttpStatus.BAD_REQUEST);
        if (distance <= 0)
            return new ResponseEntity("Invalid distance", HttpStatus.BAD_REQUEST);

        if (numberOfPeople <= 4) {
            addPrice(base4,hm4,17);
            return new ResponseEntity("The rent for 4-seater Cab for " + distance + " kilometers is " + hm4.get(distance), HttpStatus.OK);
        } else if (numberOfPeople <= 6) {
            addPrice(base6,hm6,20);
            return new ResponseEntity("The rent for 6-seater Cab for " + distance + " kilometers is " + hm6.get(distance), HttpStatus.OK);
        } else if (numberOfPeople <= 8) {
            addPrice(base8,hm8,22);
            return new ResponseEntity("The rent for 8-seater Cab for " + distance + " kilometers is " + hm8.get(distance), HttpStatus.OK);
        } else
            return new ResponseEntity("Maximum of 8 passengers allowed!", HttpStatus.OK);
    }

    private void addPrice(int base,HashMap<Integer,Float> hm,float perKm) {

        for(int i = 1; i <= 5; i++){
            hm.put(i,(float)base);
        }

        float total = base + perKm, minus = 2.0F;
        int above30 = (int)perKm - 4;

        for (int i = 6; i <= 50; i++) {
            hm.put(i, (float) Math.round((total * 100.0) / 100.0));
            if (i >= 30) {
                perKm = above30;
            }
            else if (i % 10 == 0) {
                total += 25;
                perKm -= minus;

            }
            total = total + perKm;
        }
    }

    @GetMapping("/getAllCabBookingsOfaCustomer/{customerId}")
    public Object getAllCabBookings(@PathVariable int customerId) {
        CabBookingResponse cabBookingResponse = new CabBookingResponse();
        CustomerDetails customerDetails;
        try{
            customerDetails = customerRepo.findByCustomerId(customerId);
            int id = customerDetails.getCustomerId();
        }catch(NullPointerException e){
            cabBookingResponse.setResponseMessage("Invalid Customer Id provided");
            cabBookingResponse.setResponseStatus("Failure");
            return new ResponseEntity(cabBookingResponse,HttpStatus.BAD_REQUEST);
        }
        return cabBookingRepository.findByCustomerId(customerId);
    }

    @PutMapping("/cancelCabBookingById/{id}")
    public Object cancelCab(@PathVariable int id){
        CabBookingResponse cabBookingResponse = new CabBookingResponse();
        CabBooking cabBooking = null;
        try{
            cabBooking = cabBookingRepository.findByTravelBookingId(id);
            int id1 = cabBooking.getTravelBookingId();
        }catch(NullPointerException e){
            cabBookingResponse.setResponseMessage("Invalid TravelBooking Id provided");
            cabBookingResponse.setResponseStatus("Failure");
            return new ResponseEntity(cabBookingResponse,HttpStatus.BAD_REQUEST);
        }
        cabBooking.setStatus("Cancelled");
        cabBookingRepository.save(cabBooking);
        return new ResponseEntity(cabBooking,HttpStatus.OK);
    }

}
