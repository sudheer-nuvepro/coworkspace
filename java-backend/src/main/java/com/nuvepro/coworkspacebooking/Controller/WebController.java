package com.nuvepro.coworkspacebooking.Controller;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class WebController {

    private static final Logger logger= LoggerFactory.getLogger(WebController.class);

    @Value("${stripe.public.key}")
    private String stripePublicKey;

    @Value("${payment.api.url}")
    private String paymentApiUrl; // the URL of the payment API


    @RequestMapping(value = "/payment",method = RequestMethod.GET)
    public String paymentHome(Model model, @RequestParam long amount, @RequestParam int bookingId){
        logger.info(String.valueOf(amount));
        model.addAttribute("stripePublicKey",stripePublicKey);
        model.addAttribute("PaymentAmount",amount);
        model.addAttribute("bookingId", bookingId);
        return "checkout";
    }








}
