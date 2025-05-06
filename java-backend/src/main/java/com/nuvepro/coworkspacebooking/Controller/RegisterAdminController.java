//package com.nuvepro.coworkspacebooking.Controller;
//
//import com.nuvepro.coworkspacebooking.Entity.UserDetail;
//import com.nuvepro.coworkspacebooking.Repository.UserDetailsRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//@Controller
//@CrossOrigin(origins = "http://localhost:3000")
//public class RegisterAdminController {
//
//    @Autowired
//    private UserDetailsRepository repo;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @GetMapping("public/register-admin")
//    public String register(Model model){
//        model.addAttribute("user",new UserDetail());
//        return "Register";
//    }
//
//    @PostMapping("/register")
//    public String processRegistrationForm(@ModelAttribute("user") UserDetail userDetail,Model model) {
//        if(userDetail.getUserName() == "" || userDetail.getPassword() == ""){
//            model.addAttribute("missingFields", true);
//            return "register";
//        }
//
//        if(repo.findByUserName(userDetail.getUserName()) == null){
//            String encodedpass = passwordEncoder.encode(userDetail.getPassword());
//            userDetail.setPassword(encodedpass);
//            userDetail.setUserType("ADMIN");
//            repo.save(userDetail);
//
//            return "redirect:/login";
//        }
//        model.addAttribute("usernameTaken", true);
//        return "register";
//    }
//
//}

