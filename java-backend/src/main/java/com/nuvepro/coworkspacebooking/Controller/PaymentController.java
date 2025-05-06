package com.nuvepro.coworkspacebooking.Controller;

import com.nuvepro.coworkspacebooking.Service.BookingService;
import com.nuvepro.coworkspacebooking.Service.PaymentService;
import com.nuvepro.coworkspacebooking.dto.CreatePayment;
import com.nuvepro.coworkspacebooking.dto.CreatePaymentResponse;
import com.nuvepro.coworkspacebooking.dto.PaymentSuccessResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    private static final Logger logger= LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private BookingController bookingController;

    @Autowired
    private BookingService bookingService;

    @Value("${stripe.public.key}")
    private String stripePublicKey;

    public String paymentId;
    public String paymentStatus;

    @PostMapping("/create-payment-intent")
    public CreatePaymentResponse createPaymentIntent(@RequestBody CreatePayment createPayment) throws StripeException {

//        new Long(calculateOrderAmount(postBody.getItems()))
        Long paymentAmount=createPayment.getPaymentAmount();
        logger.info("From create-payment-intent Total Payment Amount : "+paymentAmount);
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(paymentAmount*100)
                        .setCurrency("inr")
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();

        // Create a PaymentIntent with the order amount and currency
        PaymentIntent paymentIntent = PaymentIntent.create(params);

        paymentId=paymentIntent.getId();
        paymentStatus=paymentIntent.getStatus();
        logger.info("paymentId : "+paymentId);
        logger.info("paymentStatus :"+paymentStatus);
        return new CreatePaymentResponse(paymentIntent.getClientSecret());

    }



    @PostMapping("/payment-intent-succeeded")
    public ResponseEntity<?> handlePaymentIntentSucceeded(@RequestParam String paymentIntentId,
                                                          @RequestParam int bookingId) throws StripeException {

        PaymentSuccessResponse paymentResponse=new PaymentSuccessResponse();

        PaymentIntent paymentIntent=PaymentIntent.retrieve(paymentIntentId);
        System.out.println("paymentIntentId "+ paymentIntentId+ paymentIntent.getStatus());

        if(paymentIntent.getStatus().equals("succeeded")){
            ResponseEntity<?> response=bookingController.updateBooking(paymentIntent.getId(),
                    paymentIntent.getStatus(),bookingId);
            if(response.getStatusCode() != HttpStatus.OK){
                paymentResponse.setPaymentStatus("Failed to update booking with payment status");
                return new ResponseEntity<>(paymentResponse,HttpStatus.INTERNAL_SERVER_ERROR);
            }
            paymentResponse.setPaymentStatus("Payment Succeeded");
            return new ResponseEntity<>(paymentResponse,HttpStatus.OK);
        }
        paymentResponse.setPaymentStatus("Payment Intent Status is not succeeded");
        return new ResponseEntity<>(paymentResponse,HttpStatus.BAD_REQUEST);
    }
}
