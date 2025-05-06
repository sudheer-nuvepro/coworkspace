//package com.nuvepro.coworkspacebooking.Service;
//
//import com.nuvepro.coworkspacebooking.Repository.UserDetailsRepository;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class DatabaseLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
//
////    @Autowired
////    UserService userService;
//
//        @Autowired
//        UserDetailService userDetailService;
//
//
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws ServletException, IOException {
////        System.out.println(authentication.getPrincipal());
////        Object principal = authentication.getPrincipal();
////        System.out.println(principal instanceof MyUserDetails);
//        //Authentication myauth = (Authentication) authentication;
//        String user = authentication.getName();
//        userDetailService.updateAuthenticationType(user, "database");
//        super.onAuthenticationSuccess(request, response, authentication);
//    }
//
//}
