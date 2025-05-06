package com.nuvepro.coworkspacebooking.Controller;



import com.nuvepro.coworkspacebooking.Entity.Transactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionsController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @PostMapping("/addTransactions")
    public ResponseEntity addTransactions(@RequestBody Transactions transactions) {


        return null;
    }

}
