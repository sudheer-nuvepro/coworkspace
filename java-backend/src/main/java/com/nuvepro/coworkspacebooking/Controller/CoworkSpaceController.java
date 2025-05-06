package com.nuvepro.coworkspacebooking.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nuvepro.coworkspacebooking.Bean.UserResponse;
import com.nuvepro.coworkspacebooking.Entity.CoWorkspace;
import com.nuvepro.coworkspacebooking.Entity.CustomerDetails;
import com.nuvepro.coworkspacebooking.Entity.UserAuthentication;
import com.nuvepro.coworkspacebooking.Repository.CoWorkspaceRepository;
import com.nuvepro.coworkspacebooking.Service.CoworkspaceService;
import com.nuvepro.coworkspacebooking.Service.UserService;
import com.nuvepro.coworkspacebooking.security.filter.AuthenticationFilter;
import com.nuvepro.coworkspacebooking.security.manager.CustomAuthenticationManager;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.nuvepro.coworkspacebooking.Entity.UserAuthentication;
import com.nuvepro.coworkspacebooking.Service.UserService;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CoworkSpaceController {

    private static final Logger logger = LoggerFactory.getLogger(CoworkSpaceController.class);

    @Autowired
    CoWorkspaceRepository repo;

    @Autowired
    CoworkspaceService CoWorkSpaceservice;

    @Autowired
    UserResponse userResponse;
    @Autowired
    UserService userService;

    @Autowired
    private CustomAuthenticationManager authenticationManager;


    @PostMapping("/user/register")
    public ResponseEntity createUser(@Valid @RequestBody UserAuthentication user) {
        System.out.println("Insideeeeeeeeeee 1");
        userService.saveUser(user);
        userResponse.setResponseMessage("Sucessfully registered");
        userResponse.setResponseStatus("201 Created");
        return new ResponseEntity<>(userResponse,HttpStatus.CREATED);
    }
    @PostMapping("/authenticate")
    public void saveUser(@Valid @RequestBody UserAuthentication user ){
        AuthenticationFilter authenticationFilter=new AuthenticationFilter(authenticationManager);

    }



    @PostMapping("/addCoworkspace")
    public ResponseEntity addCoworkspace(@RequestBody CoWorkspace coWorkspace){
        AddCoworkspaceResponse addCoworkspaceResponse=new AddCoworkspaceResponse();

        if(coWorkspace.getAddress().isEmpty()){
            addCoworkspaceResponse.setResponseMessage("Address not provided.");
            addCoworkspaceResponse.setStatus("address");
            return new ResponseEntity<>(addCoworkspaceResponse, HttpStatus.BAD_REQUEST);
        } else if (coWorkspace.getCoworkingSpaceName().isEmpty()) {
            addCoworkspaceResponse.setResponseMessage("Workspace Name not provided");
            addCoworkspaceResponse.setStatus("coworkingSpaceName");
            return new ResponseEntity<>(addCoworkspaceResponse, HttpStatus.BAD_REQUEST);
        } else if (coWorkspace.getTotalCapacity()==0 ) {
            addCoworkspaceResponse.setResponseMessage("Capacity not provided");
            addCoworkspaceResponse.setStatus("totalCapacity");
            return new ResponseEntity<>(addCoworkspaceResponse, HttpStatus.BAD_REQUEST);

        }else if(coWorkspace.getRating()<=0 || coWorkspace.getRating()>5){
            addCoworkspaceResponse.setStatus("rating");
            addCoworkspaceResponse.setResponseMessage("Invalid rating provided.");
            return new ResponseEntity(addCoworkspaceResponse,HttpStatus.BAD_REQUEST);
        } else if (coWorkspace.getPhoneNumber().isEmpty() || coWorkspace.getPhoneNumber().isBlank()) {
            addCoworkspaceResponse.setResponseMessage("Phone number is not provided");
            addCoworkspaceResponse.setStatus("phoneNumber");
            return new ResponseEntity<>(addCoworkspaceResponse, HttpStatus.BAD_REQUEST);
        } else if (coWorkspace.getCompanyEmail().isEmpty() || coWorkspace.getCompanyEmail().isBlank()) {
            addCoworkspaceResponse.setResponseMessage("Company mail ID is not provided");
            addCoworkspaceResponse.setStatus("companyEmail");
            return new ResponseEntity<>(addCoworkspaceResponse, HttpStatus.BAD_REQUEST);
        } else if (coWorkspace.getCity().isEmpty() || coWorkspace.getCity().isBlank()) {
            addCoworkspaceResponse.setResponseMessage("City is not provided");
            addCoworkspaceResponse.setStatus("city");
            return new ResponseEntity<>(addCoworkspaceResponse, HttpStatus.BAD_REQUEST);
        } else if (coWorkspace.getState().isEmpty() || coWorkspace.getState().isBlank()) {
            addCoworkspaceResponse.setResponseMessage("State is not provided");
            addCoworkspaceResponse.setStatus("state");
            return new ResponseEntity<>(addCoworkspaceResponse, HttpStatus.BAD_REQUEST);
        } else if (coWorkspace.getPincode().isEmpty() || coWorkspace.getPincode().isBlank()) {
            addCoworkspaceResponse.setResponseMessage("Pincode is not provided");
            addCoworkspaceResponse.setStatus("pincode");
            return new ResponseEntity<>(addCoworkspaceResponse, HttpStatus.BAD_REQUEST);
        }


        List<CoWorkspace> coworkspaces=repo.findAll();
        boolean companyEmailexist=false;
        int emailfoundid=0;
        for(int i=0;i<coworkspaces.size();i++){
            if(coWorkspace.getCompanyEmail().equals(coworkspaces.get(i).getCompanyEmail())){
                companyEmailexist=true;
                emailfoundid=coworkspaces.get(i).getWorkspaceId();
                break;
            }
        }

        if(!companyEmailexist){

            CoWorkspace coworkspace=new CoWorkspace();
            coworkspace=repo.save(coWorkspace);
            logger.info("cabfacilityavailability: " + coWorkspace.isCabFacilityAvailable());
            addCoworkspaceResponse.setCoworkspaceId(coworkspace.getWorkspaceId());
            addCoworkspaceResponse.setResponseMessage("CoWorkSpace added successfully");
            addCoworkspaceResponse.setStatus("Success");
            return new ResponseEntity<>(addCoworkspaceResponse, HttpStatus.CREATED);


        }else{
            addCoworkspaceResponse.setResponseMessage("CoWorkspace Company email already exists");
            addCoworkspaceResponse.setStatus("Request Rejected");
            return new ResponseEntity<>(addCoworkspaceResponse, HttpStatus.BAD_REQUEST);
        }

    }


    @GetMapping("/getCoworkspaceById/{coWorkspaceid}")
    public ResponseEntity getCoworkspaceById(@PathVariable(value="coWorkspaceid") int id){
        getCoworkspaceResponse getCoworkspaceResponse=new getCoworkspaceResponse();

        if(id<=0){
            getCoworkspaceResponse.setCoworkspaceId(id);
            getCoworkspaceResponse.setResponseMessage("Invalid id provided");
            getCoworkspaceResponse.setStatus("Request Rejected");
            return new ResponseEntity<>(getCoworkspaceResponse, HttpStatus.OK);
        } else if (CoWorkSpaceservice.checkIfCoworkSpaceExists(id)) {
            logger.info("CoworkSpace exists. Fetching the CoworkSpace details for the id: " + id);
            CoWorkspace coWorkspace =repo.findById(id).get();

            return new ResponseEntity<>(coWorkspace, HttpStatus.OK);
        }else{
            logger.info("CoworkSpace doesn't exists. Check the CoworkSpace id: " + id);
            getCoworkspaceResponse.setCoworkspaceId(id);
            getCoworkspaceResponse.setResponseMessage("CoworkSpace doesn't exists. Check the CoworkSpace id.");
            getCoworkspaceResponse.setStatus("Request Rejected");
            return new ResponseEntity<>(getCoworkspaceResponse,HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/getAllCoworkspaces")
    public ResponseEntity getAllCoworkspaces(){
        CoworkspaceResponse coworkspaceResponse=new CoworkspaceResponse();

        List<CoWorkspace> Coworkspaces=repo.findAll();

        if(Coworkspaces.size()<=0){
            coworkspaceResponse.setStatus("Request Rejected");
            coworkspaceResponse.setResponseMessage("No Coworkspace found");
            return new ResponseEntity<>(coworkspaceResponse,HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(Coworkspaces,HttpStatus.OK);
        }
    }

    @GetMapping("/getCoworksoaceByCityOrPincode")
    public ResponseEntity getCoworkspaceBycityorpincode(@RequestParam(value = "city", required = false) String city,@RequestParam(value = "pincode", required = false) String pincode) {
        CoworkspaceResponse coworkspaceResponse=new CoworkspaceResponse();
        List<CoWorkspace> coWorkspaceEntities=new ArrayList<>();
        boolean integer=isNumeric(pincode);
        if((city.isEmpty() || city.isBlank()) && (!pincode.isEmpty() || !pincode.isBlank()) && integer){
            if(pincode.length()<=5 && pincode.length()>6){
                coworkspaceResponse.setStatus("pincode");
                coworkspaceResponse.setResponseMessage("Invalid pincode provided.");
                return new ResponseEntity<>(coworkspaceResponse,HttpStatus.BAD_REQUEST);
            }else{
                coWorkspaceEntities=repo.findByPincodeIgnoreCase(pincode);
                return new ResponseEntity<>(coWorkspaceEntities,HttpStatus.OK);
            }

        } else if ((pincode.isBlank() || pincode.isEmpty()) && (!city.isEmpty() || !city.isBlank())) {
            Long cities=repo.countByCityIgnoreCase(city);
            if(cities>0){
                coWorkspaceEntities=repo.findByCityIgnoreCase(city);
                return new ResponseEntity<>(coWorkspaceEntities,HttpStatus.OK);
            }else{

                coworkspaceResponse.setResponseMessage("Invalid city provided or no records found");
                coworkspaceResponse.setStatus("city");
                return new ResponseEntity<>(coworkspaceResponse,HttpStatus.BAD_REQUEST);
            }
        }else if((pincode.isBlank() || pincode.isEmpty()) && (city.isBlank() || city.isEmpty())){

            coworkspaceResponse.setResponseMessage("city and pincode are not provided");
            coworkspaceResponse.setStatus("cityandpincode");
            return new ResponseEntity<>(coworkspaceResponse,HttpStatus.BAD_REQUEST);

        }else {
            coWorkspaceEntities=repo.findByPincodeIgnoreCaseAndCityIgnoreCase(pincode,city);

            return new ResponseEntity(coWorkspaceEntities,HttpStatus.OK);
        }

    }

    @GetMapping("/getCoworkspaceByCity")
    public ResponseEntity getCoworkspaceBycity(@RequestParam(value = "city") String city) {
        CoworkspaceResponse coworkspaceResponse=new CoworkspaceResponse();
        List<CoWorkspace> coWorkspaces=new ArrayList<>();
        Long cities=0L;
        if(city.isEmpty() || city.isBlank()){
            coworkspaceResponse.setResponseMessage("City not provided");
            coworkspaceResponse.setStatus("city");

        } else{
            cities=repo.countByCityContainsIgnoreCase(city);

        }
        if(cities>0){
            coWorkspaces=repo.findByCityContainsIgnoreCase(city);
            return new ResponseEntity<>(coWorkspaces,HttpStatus.OK);
        }else{
            coworkspaceResponse.setResponseMessage("Invalid city provided or no records found");
            coworkspaceResponse.setStatus("city");
            return new ResponseEntity<>(coworkspaceResponse,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/getCoworkspaceByPincode")
    public ResponseEntity getCoworkspaceByPincode(@RequestParam(value = "pincode") String pincode) {
        CoworkspaceResponse coworkspaceResponse=new CoworkspaceResponse();
        List<CoWorkspace> coWorkspaceEntities=new ArrayList<>();
        boolean integer=isNumeric(pincode);

        if(pincode.isBlank() || pincode.isEmpty()){
            coworkspaceResponse.setResponseMessage("Pincode not provided");
            coworkspaceResponse.setStatus("pincode");
            return new ResponseEntity<>(coworkspaceResponse,HttpStatus.BAD_REQUEST);
        } else {
            if(pincode.length()<=5 || pincode.length()>6 || !integer){
                coworkspaceResponse.setStatus("pincode");
                coworkspaceResponse.setResponseMessage("Invalid pincode provided.");
                return new ResponseEntity<>(coworkspaceResponse,HttpStatus.BAD_REQUEST);
            }else{
                coWorkspaceEntities=repo.findByPincodeIgnoreCase(pincode);

                if(coWorkspaceEntities.size()>0){
                    return new ResponseEntity<>(coWorkspaceEntities,HttpStatus.OK);
                }else {
                    coworkspaceResponse.setStatus("pincode");
                    coworkspaceResponse.setResponseMessage("Invalid pincode provided or no records found");
                    return new ResponseEntity<>(coworkspaceResponse,HttpStatus.BAD_REQUEST);
                }

            }

        }

    }


    @GetMapping("/getCoworkSpaceByName")
    public ResponseEntity getCoworkSpaceByName(@RequestParam String coworkspaceName){
        CoworkspaceResponse coworkspaceResponse=new CoworkspaceResponse();
        List<CoWorkspace> coworkSpaces=new ArrayList<>();
        if(coworkspaceName.isEmpty() || coworkspaceName.isBlank()){
            coworkspaceResponse.setResponseMessage("Coworkspace name not provided");
            coworkspaceResponse.setStatus("coworkingSpaceName");
            return new ResponseEntity<>(coworkspaceResponse,HttpStatus.BAD_REQUEST);
        }else {
            coworkSpaces=repo.findByCoworkingSpaceNameContainsIgnoreCase(coworkspaceName);
        }
        if(coworkSpaces.size()>0){
            logger.info("Matching CoworkSpaces available for the name: " + coworkspaceName);
            return new ResponseEntity(coworkSpaces,HttpStatus.OK);

        }else {
            logger.info("Failed to get coworkspace records for the name: " + coworkspaceName);
            coworkspaceResponse.setResponseMessage("Failed to get coworkspace records for the name: " + coworkspaceName);
            coworkspaceResponse.setStatus("coworkspaceName");
            return new ResponseEntity<>(coworkspaceResponse,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getCoworkspaceByCabFacilityAvailable")
    public ResponseEntity getCoworkspaceByCabFacilityAvailable(){
        CoworkspaceResponse coworkspaceResponse=new CoworkspaceResponse();
        List<CoWorkspace> coworkSpaces=new ArrayList<>();
        coworkSpaces=repo.findByIsCabFacilityAvailableTrue();
        if(coworkSpaces.size()<=0){
            coworkspaceResponse.setStatus("Request Rejected");
            coworkspaceResponse.setResponseMessage("No Coworkspace available with cab facility.");
            return new ResponseEntity<>(coworkspaceResponse,HttpStatus.BAD_REQUEST);
        }else{
            logger.info("Coworkspaces with cab facility available");
            return new ResponseEntity(coworkSpaces,HttpStatus.OK);
        }

    }

    @GetMapping("/getCoworkspaceByCabAvailableByCity")
    public ResponseEntity getCoworkspaceByCabFacilityAvailableByCity(@RequestParam String city){
        CoworkspaceResponse coworkspaceResponse=new CoworkspaceResponse();
        List<CoWorkspace> coworkSpaces=new ArrayList<>();
        coworkSpaces=repo.findByIsCabFacilityAvailableTrueAndCityContainsIgnoreCase(city);
        if(city.isBlank() || city.isEmpty()){
            coworkspaceResponse.setStatus("city");
            coworkspaceResponse.setResponseMessage("city provided is invalid or empty");
            return new ResponseEntity<>(coworkspaceResponse,HttpStatus.BAD_REQUEST);
        }
        if(coworkSpaces.size()<=0){
            coworkspaceResponse.setStatus("Request Rejected");
            coworkspaceResponse.setResponseMessage("No Coworkspace available with cab facility in this city.");
            return new ResponseEntity<>(coworkspaceResponse,HttpStatus.BAD_REQUEST);
        }else{
            logger.info("Coworkspaces with cab facility available");
            return new ResponseEntity(coworkSpaces,HttpStatus.OK);
        }

    }


    @GetMapping("/getCountOfCoworkspacesByCabFacilityAvailable")
    public ResponseEntity getCountOfCoworkspacesByCabFacilityAvailable(){
        CoworkspaceResponse coworkspaceResponse=new CoworkspaceResponse();

        long noOfCoworkspaces=repo.countByIsCabFacilityAvailableTrue();

        if(noOfCoworkspaces<=0){
            coworkspaceResponse.setResponseMessage("No Coworkspace available");
            coworkspaceResponse.setStatus("Request Rejected");
            return new ResponseEntity<>(coworkspaceResponse,HttpStatus.BAD_REQUEST);
        }else{
            coworkspaceResponse.setStatus("Success");
            coworkspaceResponse.setResponseMessage("Total Number of Coworkspace with cab facility availability:"+noOfCoworkspaces);
            return new ResponseEntity<>(coworkspaceResponse,HttpStatus.OK);
        }
    }

    @GetMapping("/getCountOfCoworkspacesByPincode")
    public ResponseEntity getCountOfCoworkspacesByPincode(@RequestParam String pincode){
        CoworkspaceResponse coworkspaceResponse=new CoworkspaceResponse();

        boolean integer=isNumeric(pincode);

        if((!pincode.isEmpty() || !pincode.isBlank()) && integer){
            if(pincode.length()<=5 && pincode.length()>6){
                coworkspaceResponse.setStatus("pincode");
                coworkspaceResponse.setResponseMessage("Invalid pincode provided.");
                return new ResponseEntity<>(coworkspaceResponse,HttpStatus.BAD_REQUEST);
            }else{
                long noOfCoworkspaces=repo.countByPincodeIgnoreCase(pincode);

                if(noOfCoworkspaces<=0){
                    coworkspaceResponse.setResponseMessage("No Coworkspace available");
                    coworkspaceResponse.setStatus("Request Rejected");
                    return new ResponseEntity<>(coworkspaceResponse,HttpStatus.BAD_REQUEST);
                }else{
                    coworkspaceResponse.setStatus("Success");
                    coworkspaceResponse.setResponseMessage("Total Number of Coworkspace with pincode "+pincode+" : "+noOfCoworkspaces);
                    return new ResponseEntity<>(coworkspaceResponse,HttpStatus.OK);
                }
            }

        }else {
            coworkspaceResponse.setStatus("pincode");
            coworkspaceResponse.setResponseMessage("Invalid pincode provided.");
            return new ResponseEntity<>(coworkspaceResponse,HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/updateCoworkspaceById/{id}")
    public ResponseEntity updateCoworkspaceById(@PathVariable int id,@RequestBody CoWorkspace coWorkspace){
        CoworkspaceResponse coworkspaceResponse=new CoworkspaceResponse();
        if(id<=0){
            coworkspaceResponse.setResponseMessage("Invalid id provided");
            coworkspaceResponse.setStatus("Request Rejected");
            return new ResponseEntity<>(coworkspaceResponse,HttpStatus.BAD_REQUEST);
        }

        if(coWorkspace.getAddress().isEmpty()){
            coworkspaceResponse.setResponseMessage("Address not provided.");
            coworkspaceResponse.setStatus("address");
            return new ResponseEntity<>(coworkspaceResponse, HttpStatus.BAD_REQUEST);
        } else if (coWorkspace.getCoworkingSpaceName().isEmpty()) {
            coworkspaceResponse.setResponseMessage("Workspace Name not provided");
            coworkspaceResponse.setStatus("coworkingSpaceName");
            return new ResponseEntity<>(coworkspaceResponse, HttpStatus.BAD_REQUEST);
        } else if (coWorkspace.getTotalCapacity()==0 ) {
            coworkspaceResponse.setResponseMessage("Capacity not provided");
            coworkspaceResponse.setStatus("totalCapacity");
            return new ResponseEntity<>(coworkspaceResponse, HttpStatus.BAD_REQUEST);

        }else if(coWorkspace.getRating()<=0 || coWorkspace.getRating()>5){
            coworkspaceResponse.setStatus("rating");
            coworkspaceResponse.setResponseMessage("Invalid rating provided.");
            return new ResponseEntity(coworkspaceResponse,HttpStatus.BAD_REQUEST);
        } else if (coWorkspace.getPhoneNumber().isEmpty() || coWorkspace.getPhoneNumber().isBlank()) {
            coworkspaceResponse.setResponseMessage("Phone number is not provided");
            coworkspaceResponse.setStatus("phoneNumber");
            return new ResponseEntity<>(coworkspaceResponse, HttpStatus.BAD_REQUEST);
        } else if (coWorkspace.getCompanyEmail().isEmpty() || coWorkspace.getCompanyEmail().isBlank()) {
            coworkspaceResponse.setResponseMessage("Company mail ID is not provided");
            coworkspaceResponse.setStatus("companyEmail");
            return new ResponseEntity<>(coworkspaceResponse, HttpStatus.BAD_REQUEST);
        } else if (coWorkspace.getCity().isEmpty() || coWorkspace.getCity().isBlank()) {
            coworkspaceResponse.setResponseMessage("City is not provided");
            coworkspaceResponse.setStatus("city");
            return new ResponseEntity<>(coworkspaceResponse, HttpStatus.BAD_REQUEST);
        } else if (coWorkspace.getState().isEmpty() || coWorkspace.getState().isBlank()) {
            coworkspaceResponse.setResponseMessage("State is not provided");
            coworkspaceResponse.setStatus("state");
            return new ResponseEntity<>(coworkspaceResponse, HttpStatus.BAD_REQUEST);
        } else if (coWorkspace.getPincode().isEmpty() || coWorkspace.getPincode().isBlank()) {
            coworkspaceResponse.setResponseMessage("Pincode is not provided");
            coworkspaceResponse.setStatus("pincode");
            return new ResponseEntity<>(coworkspaceResponse, HttpStatus.BAD_REQUEST);
        }


        List<CoWorkspace> coworkspaces=repo.findAll();
        boolean companyEmailexist=false;
        boolean coworkspaceId=false;
        int emailfoundid=0;
        for(int i=0;i<coworkspaces.size();i++){
            if(coWorkspace.getCompanyEmail().equals(coworkspaces.get(i).getCompanyEmail())&&coworkspaces.get(i).getWorkspaceId()!=id){
                companyEmailexist=true;
                emailfoundid=coworkspaces.get(i).getWorkspaceId();
                break;
            }

        }
        if(repo.existsById(id)){
            coworkspaceId=true;
        }
        if(coworkspaceId){

            if(!companyEmailexist){


                CoWorkspace coWorkspace1=repo.findById(id).get();
                coWorkspace1.setCoworkingSpaceName(coWorkspace.getCoworkingSpaceName());
                coWorkspace1.setTotalCapacity(coWorkspace.getTotalCapacity());
                coWorkspace1.setPhoneNumber(coWorkspace.getPhoneNumber());
                coWorkspace1.setCompanyEmail(coWorkspace.getCompanyEmail());
                coWorkspace1.setAddress(coWorkspace.getAddress());
                coWorkspace1.setCity(coWorkspace.getCity());
                coWorkspace1.setState(coWorkspace.getState());
                coWorkspace1.setCabFacilityAvailable(coWorkspace.isCabFacilityAvailable());
                coWorkspace1.setPincode(coWorkspace.getPincode());
                coWorkspace1.setRating(coWorkspace.getRating());
//                coWorkspace1.setRoom(coWorkspace.getRoom());
//                logger.info("room "+coWorkspace1.getRoom().toString());
                repo.save(coWorkspace1);
                logger.info(String.valueOf(coWorkspace1.getWorkspaceId())+"Coworkspace details updated successfully.");
                coworkspaceResponse.setResponseMessage("Coworkspace details updated successfullywith coworkspaceId"+id);
                coworkspaceResponse.setStatus("Success");
                return new ResponseEntity<>(coworkspaceResponse,HttpStatus.OK);
            }else{
                coworkspaceResponse.setResponseMessage("Email already exists");
                coworkspaceResponse.setStatus("Request Rejected");
                return new ResponseEntity<>(coworkspaceResponse,HttpStatus.BAD_REQUEST);
            }


        }else{
            coworkspaceResponse.setResponseMessage("Coworkspace Id provided is invalid");
            coworkspaceResponse.setStatus("Request Rejected");
            return new ResponseEntity<>(coworkspaceResponse,HttpStatus.BAD_REQUEST);
        }



    }




    @DeleteMapping("/deleteCoworkspaceById/{id}")
    public ResponseEntity deleteCoworkspaceById(@PathVariable int id){

        CoworkspaceResponse coworkspaceResponse=new CoworkspaceResponse();

        if(id<=0){
            coworkspaceResponse.setResponseMessage("Invalid Id provided");
            coworkspaceResponse.setStatus("workspaceId");
            return new ResponseEntity(coworkspaceResponse,HttpStatus.BAD_REQUEST);
        }

        List<CoWorkspace> coworkspaces=repo.findAll();
        boolean companyexist=false;
        int foundid=0;
        for(int i=0;i<coworkspaces.size();i++){
            if(coworkspaces.get(i).getWorkspaceId()==id){
                companyexist=true;
                foundid=coworkspaces.get(i).getWorkspaceId();
                break;
            }
        }

        if(companyexist){
            repo.deleteById(id);
            coworkspaceResponse.setResponseMessage("Coworkspace deleted successfully");
            coworkspaceResponse.setStatus("Success");
            return new ResponseEntity(coworkspaceResponse,HttpStatus.OK);
        }else{
            coworkspaceResponse.setResponseMessage("coworkspace does not exist for Id : "+id);
            coworkspaceResponse.setStatus("Request Rejected");
            return new ResponseEntity(coworkspaceResponse,HttpStatus.OK);
        }

    }



    public static boolean isNumeric(String string) {
        int intValue;

//        System.out.println(String.format("Parsing string: \"%s\"", string));

        if(string == null || string.equals("")) {
            System.out.println("String cannot be parsed, it is null or empty.");
            return false;
        }

        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer.");
        }
        return false;
    }

}
