//package com.nuvepro.coworkspacebooking.Service;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//import java.io.IOException;
//
//@Component
//public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {
//
//    @Autowired
//    UserDetailService userService;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws ServletException, IOException {
//        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
//        String oauth2ClientName = oauth2User.getOauth2ClientName();
//        String username = oauth2User.getEmail();
//        userService.processOAuthPostLogin(username);
//        userService.updateAuthenticationType(username, oauth2ClientName);
//
//        //super.onAuthenticationSuccess(request, response, authentication);
//
//        response.sendRedirect("/");
//    }
//
//}
