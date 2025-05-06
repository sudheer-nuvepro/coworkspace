package com.nuvepro.coworkspacebooking.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class PaymentService {

    @Value("${stripe.secret.key}")
    private String stripeApiKey;


    public PaymentIntent retrievePayment(String paymentIntentId) {
        Stripe.apiKey = stripeApiKey;
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            return paymentIntent;
        } catch (StripeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean handlePaymentResponse(String paymentIntentId) {
        Stripe.apiKey = stripeApiKey;


        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

            // Check payment status
            if (paymentIntent.getStatus().equals("succeeded")) {
                // Payment succeeded, update your database or send confirmation email
                return true;
            } else {
                // Payment failed or requires action, handle accordingly
                return false;
            }
        } catch (StripeException e) {
            e.printStackTrace();
            return false;
        }
    }
}