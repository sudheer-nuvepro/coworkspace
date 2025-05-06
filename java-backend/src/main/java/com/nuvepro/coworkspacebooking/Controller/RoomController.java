package com.nuvepro.coworkspacebooking.Controller;


import com.nuvepro.coworkspacebooking.Entity.CoWorkspace;
import com.nuvepro.coworkspacebooking.Entity.Room;
import com.nuvepro.coworkspacebooking.Repository.CoWorkspaceRepository;
import com.nuvepro.coworkspacebooking.Repository.RoomRepository;
import com.nuvepro.coworkspacebooking.Service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class RoomController {

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);


    @Autowired
    RoomRepository repo;

    @Autowired
    private BookingService bookingService;

    @Autowired
    CoWorkspaceRepository coWorkspaceRepository;

    @PostMapping("/addRoomsInCoworkspaceRoom/{workspaceId}")
    public ResponseEntity addRoomsInCoworkspaceRoom(@RequestBody Room room,
                                                    @PathVariable int workspaceId){

        RoomResponse response=new RoomResponse();
        List<CoWorkspace> coworkspaces=coWorkspaceRepository.findAll();
        CoWorkspace coWorkspace=coWorkspaceRepository.findByWorkspaceId(workspaceId);
        List<Room> workspaces=repo.findAll();
        boolean companyRoomNumberExist=false;
        int roomnumber=0;
        boolean workspaceidExist=false;
        for(int i=0;i<coworkspaces.size();i++){
            if(workspaceId==(coworkspaces.get(i).getWorkspaceId())){
                workspaceidExist=true;
                break;
            }
        }

        if(room.getFloor()<=0 ){
            response.setStatus("floor");
            response.setResponseMessage("Invalid floor number provided.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }else if (room.getRoomSeatingCapacity()<=0){
            response.setStatus("seatingCapacity");
            response.setResponseMessage("Invalid seating capacity provided.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }else if(room.getRoomType().isEmpty() || room.getRoomType().isBlank()){
            response.setStatus("roomType");
            response.setResponseMessage("Room type not provided.");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        } else if (workspaceidExist==false) {
            response.setStatus("workspaceId");
            response.setResponseMessage("Invalid workspaceId provided.");
            return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
        } else{

            room.setWorkspace(coWorkspace);
            for(int i=0;i<workspaces.size();i++){
                if(room.getRoomNumber()==workspaces.get(i).getRoomNumber() &&
                        room.getWorkspace().equals(coWorkspace)){
                    //logger.info("already there : "+workspaceId);
                    companyRoomNumberExist=true;
                    roomnumber=workspaces.get(i).getRoomNumber();
                    break;
                }
            }
        }

        if(!companyRoomNumberExist){

            Room room1=new Room();
            room1=repo.save(room);
            logger.info("coworkspace details" +room1.getWorkspace().getCity());
            response.setRoomNumber(room1.getRoomNumber());
            response.setResponseMessage("New Room added successfully");
            response.setStatus("Success");
            return new ResponseEntity<>(response, HttpStatus.CREATED);


        }else {
//            logger.info("Coworkspace Room Number already exists in this Coworkspace");
            response.setResponseMessage("Coworkspace Room Number already exists in this Coworkspace");
            response.setStatus("Request Rejected");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/updateRoomById/{id}")
    public ResponseEntity updateRoomById(@PathVariable int id,@RequestBody Room room) {
        RoomResponse roomResponse=new RoomResponse();
        if(id<=0){
            roomResponse.setResponseMessage("Invalid id provided");
            roomResponse.setStatus("Request Rejected");
            return new ResponseEntity<>(roomResponse,HttpStatus.BAD_REQUEST);
        }
        List<CoWorkspace> coworkspaces=coWorkspaceRepository.findAll();
        CoWorkspace coWorkspace=coWorkspaceRepository.findByWorkspaceId(id);
        List<Room> workspaces=repo.findAll();
        boolean companyRoomNumberExist=false;
        int roomnumber=0;
        boolean workspaceidExist=false;
        for(int i=0;i<coworkspaces.size();i++){
            if(id==(coworkspaces.get(i).getWorkspaceId())){
                workspaceidExist=true;
                break;
            }
        }

        if(room.getFloor()<=0 ){
            roomResponse.setStatus("floor");
            roomResponse.setResponseMessage("Invalid floor number provided.");
            return new ResponseEntity<>(roomResponse, HttpStatus.BAD_REQUEST);
        }else if (room.getRoomSeatingCapacity()<=0){
            roomResponse.setStatus("seatingCapacity");
            roomResponse.setResponseMessage("Invalid seating capacity provided.");
            return new ResponseEntity<>(roomResponse, HttpStatus.BAD_REQUEST);
        }else if(room.getRoomType().isEmpty() || room.getRoomType().isBlank()) {
            roomResponse.setStatus("roomType");
            roomResponse.setResponseMessage("Room type not provided.");
            return new ResponseEntity<>(roomResponse, HttpStatus.BAD_REQUEST);
        }else{

            room.setWorkspace(coWorkspace);
            for(int i=0;i<workspaces.size();i++){
                if(room.getRoomNumber()==workspaces.get(i).getRoomNumber() && workspaces.get(i).getRoomId()==id
                      &&  room.getWorkspace().equals(coWorkspace)){
//                logger.info("already there : "+workspaces.get(i).getRoomId() +"  "+id);
                    companyRoomNumberExist=true;
                    roomnumber=workspaces.get(i).getRoomNumber();
                    break;
                }
            }
        }

        if(companyRoomNumberExist){

            Room room1=repo.findByRoomId(id);
            room1.setRoomNumber(room.getRoomNumber());
            room1.setAvailable(room.isAvailable());
            room1.setRoomType(room.getRoomType());
            room1.setFloor(room.getFloor());
            room1.setRoomSeatingCapacity(room.getRoomSeatingCapacity());
            room1.setRoomRentPerHour(room.getRoomRentPerHour());
            room1.setRoomRentPerHour(room.getRoomRentPerHour());
            room1.setRoomRentByDay(room.getRoomRentByDay());
//            room.setWorkspace(workspaceId);
            room1=repo.save(room);
            logger.info("coworkspace  details" +room1.getWorkspace().getCoworkingSpaceName());
            roomResponse.setRoomNumber(room1.getRoomNumber());
            roomResponse.setResponseMessage("Room updated successfully with roomId : "+id);
            roomResponse.setStatus("Success");
            return new ResponseEntity<>(roomResponse, HttpStatus.OK);


        }else {
//            logger.info("Coworkspace Room Number already exists in this Coworkspace");
            roomResponse.setResponseMessage("Coworkspace Room Id does not exist");
            roomResponse.setStatus("Request Rejected");
            return new ResponseEntity<>(roomResponse, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/getAllRooms")
    public ResponseEntity getAllRooms(){
        List<Room> roomtypes=new ArrayList<>();
        roomtypes=repo.findAll();

        return new ResponseEntity(roomtypes, HttpStatus.OK);
    }




    @GetMapping("/getAvailableRooms")
    public List<Room> getAvailableRooms(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        logger.info("startTime : "+startTime+" endTime : "+endTime);
        return bookingService.getAvailableRooms(startTime, endTime);
    }

    @GetMapping("/availableRoomsByWorkspaceId/{workspaceId}")
    public List<Room> getAvailableRoomsByWorkspaceId(@PathVariable int workspaceId, @RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {

        return bookingService.getAvailableRoomsByWorkspaceId(workspaceId,startTime, endTime);
    }
}
