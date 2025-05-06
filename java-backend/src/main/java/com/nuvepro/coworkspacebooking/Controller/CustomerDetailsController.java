package com.nuvepro.coworkspacebooking.Controller;


import com.nuvepro.coworkspacebooking.Bean.CustomerRequest;
import com.nuvepro.coworkspacebooking.Entity.CustomerDetails;
import com.nuvepro.coworkspacebooking.Entity.UserAuthentication;
import com.nuvepro.coworkspacebooking.Entity.UserDetail;
import com.nuvepro.coworkspacebooking.Repository.CustomerRepository;
import com.nuvepro.coworkspacebooking.Repository.UserDetailsRepository;
import com.nuvepro.coworkspacebooking.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping()
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerDetailsController {

    @Autowired
    private CustomerRepository customerRepo;
   @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger= LoggerFactory.getLogger(BookingController.class);

    @PostMapping("/addNewCustomer")
    public ResponseEntity addNewCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerResponse customerResponse=new CustomerResponse();
        CustomerDetails customer = new CustomerDetails();
        List<CustomerDetails> customers=customerRepo.findAll();
        boolean customerEmailexist=false;
        int emailfoundid=0;
        for(int i=0;i<customers.size();i++){
            if(customerRequest.getCustomerEmail().equals(customers.get(i).getCustomerEmail())){
                customerEmailexist=true;
                emailfoundid=customers.get(i).getCustomerId();
                break;
            }
        }

            Optional<UserAuthentication> user = userRepository.findByUsername(customerRequest.getCustomerEmail());
            if(user.isEmpty()){
                customerResponse.setResponseMessage("Customer is not registered yet");
                 customerResponse.setResponseStatus("Request Rejected");
                return new ResponseEntity(customerResponse, HttpStatus.BAD_REQUEST);}

            else if(customerEmailexist){
                customerResponse.setResponseMessage("Customer already exits");
                customerResponse.setResponseStatus("Request Rejected");
                return new ResponseEntity(customerResponse, HttpStatus.BAD_REQUEST);}


            else{
                CustomerDetails customerDetails = new CustomerDetails();
                customer.setCustomerEmail(customerRequest.getCustomerEmail());
                customer.setCity(customerRequest.getCity());
            customer.setCustomerFirstName(customerRequest.getCustomerFirstName());
            customer.setCustomerLastName(customerRequest.getCustomerLastName());
            customer.setCompanyName(customerRequest.getCompanyName());
            customer.setCountry(customerRequest.getCountry());
            customer.setPincode(customerRequest.getPincode());
            customer.setCompanyAddress(customerRequest.getCompanyAddress());
            customer.setPhoneNumber(customerRequest.getPhoneNumber());
            customer.setState(customerRequest.getState());
            customer.setTitle(customerRequest.getTitle());
            customerDetails = customerRepo.save(customer);
            customerResponse.setResponseMessage("Customer created successfully for id :" + customerDetails.getCustomerId());
            customerResponse.setResponseStatus("Success");
//            UserDetail userDetail = new UserDetail();
//            userDetail.setUserName(customerRequest.getCustomerEmail());
//            userDetail.setPassword(customerRequest.getPassword());
//            userDetail.setUserType("USER");
//            processRegistrationForm(userDetail);
            return new ResponseEntity(customerResponse, HttpStatus.OK);
        }

    }

//    public String processRegistrationForm(@ModelAttribute("user") UserDetail userDetail) {
//        String encodedpass = passwordEncoder.encode(userDetail.getPassword());
//        userDetail.setPassword(encodedpass);
//        userDetail.setUserType(userDetail.getUserType().toUpperCase());
//        repo.save(userDetail);
//        return "redirect:/login";
//    }


    @GetMapping("/getAllCustomers")
    public List<CustomerDetails> getAllCustomers(){
        return customerRepo.findAll();
    }

    @PutMapping("/updateCustomerById/{id}")
    public ResponseEntity updateCustomerById(@PathVariable int id,@RequestBody CustomerDetails customer){
        CustomerResponse customerResponse=new CustomerResponse();

        if(id<=0){
            customerResponse.setResponseMessage("Invalid id provided");
            customerResponse.setResponseStatus("Request Rejected");
            return new ResponseEntity<>(customerResponse,HttpStatus.BAD_REQUEST);
        }
        List<CustomerDetails> customers=customerRepo.findAll();
        boolean emailExist=false;
        for(int i=0;i<customers.size();i++){
            if(customer.getCustomerEmail().equals(customers.get(i).getCustomerEmail()) && customers.get(i).getCustomerId()!=id){
                emailExist=true;
                break;
            }
        }

        if(emailExist){
            customerResponse.setResponseMessage("Email already exists");
            customerResponse.setResponseStatus("Request Rejected");
            return new ResponseEntity<>(customerResponse,HttpStatus.BAD_REQUEST);
        }

        try{
            logger.info("Updating the Customer details for the Customer id: " + id);

            CustomerDetails customerDetails=customerRepo.findById(id).get();

            customerDetails.setCustomerFirstName(customer.getCustomerFirstName());
            customerDetails.setCustomerLastName(customer.getCustomerLastName());
            customerDetails.setCustomerEmail(customer.getCustomerEmail());
            customerDetails.setCompanyAddress(customer.getCompanyAddress());
            customerDetails.setPincode(customer.getPincode());
            customerDetails.setPhoneNumber(customer.getPhoneNumber());
            customerDetails.setCity(customer.getCity());
            customerDetails.setState(customer.getState());
            customerDetails.setCountry(customer.getCountry());
            customerDetails.setCompanyName(customer.getCompanyName());
            customerDetails.setTitle(customer.getTitle());


            customerRepo.save(customer);
            logger.info(String.valueOf(customerDetails.getCustomerId()));
            customerResponse.setResponseMessage("Customer details updated successfully.");

            customerResponse.setResponseStatus("Success");
            logger.info("Customer Details Response Id : "+ customerDetails.getCustomerId());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Status", "Success");
            return new ResponseEntity(customerResponse,headers, HttpStatus.OK);


        }catch(NoSuchElementException e){
            logger.info("Customer details not found for the customer id: " + id);
            customerResponse.setResponseMessage("Customer details not found for the customer id: " + id);
            customerResponse.setResponseStatus(String.valueOf(id));
            return new ResponseEntity(customerResponse, HttpStatus.BAD_REQUEST);
        }


    }

    @DeleteMapping("/deleteCustomerById/{id}")
    public ResponseEntity deleteCustomerById(@PathVariable int id) {
        CustomerResponse customerResponse = new CustomerResponse();

        if (id <= 0) {
            customerResponse.setResponseMessage("Invalid id provided");
            customerResponse.setResponseStatus("customerId");
            return new ResponseEntity<>(customerResponse, HttpStatus.BAD_REQUEST);
        }
        List<CustomerDetails> customers = customerRepo.findAll();


        boolean customerexist=false;
        int foundid=0;
        for(int i=0;i<customers.size();i++){
            if(customers.get(i).getCustomerId()==id){
                customerexist=true;
                foundid=customers.get(i).getCustomerId();
                break;
            }
        }

        if(customerexist){
            customerRepo.deleteById(id);
            customerResponse.setResponseMessage("Customer details deleted successfully for Id : "+id);

            customerResponse.setResponseStatus("Success");
            return new ResponseEntity<>(customerResponse,HttpStatus.OK);
        }else{
            customerResponse.setResponseMessage("Invalid id provided");
            customerResponse.setResponseStatus("Request Rejected");
            return new ResponseEntity<>(customerResponse, HttpStatus.BAD_REQUEST);
        }


    }


}
