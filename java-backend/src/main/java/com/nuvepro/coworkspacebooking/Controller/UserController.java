//package com.nuvepro.coworkspacebooking.Controller;
//
//import javax.validation.Valid;
//
//
//import com.ltp.gradesubmission.bean.UserResponse;
//import com.nuvepro.coworkspacebooking.Service.UserService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ltp.gradesubmission.entity.User;
//
//import lombok.AllArgsConstructor;
//
//
//@AllArgsConstructor
//@RestController
//@RequestMapping("/user")
//public class UserController {
//
//
//    UserService userService;
//    UserResponse userResponse;
//
//    @GetMapping("/{id}")
//    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
//        userResponse.setUsername(userService.getUser(id).getUsername());
//        return new ResponseEntity<>(userResponse, HttpStatus.OK);
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody User user) {
//        userService.saveUser(user);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//
//}