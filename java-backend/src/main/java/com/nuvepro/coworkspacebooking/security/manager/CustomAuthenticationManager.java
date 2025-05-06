package com.nuvepro.coworkspacebooking.security.manager;

import com.nuvepro.coworkspacebooking.Entity.UserAuthentication;
import com.nuvepro.coworkspacebooking.Service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {
    private UserService userServiceImpl;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserAuthentication user = userServiceImpl.getUser(authentication.getName());  //based on username from authrntication obj, datastore is searchedd
        if (!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) //1st arg is from login pagge credentials and 2nd aarg is from stored data
        {
            throw new BadCredentialsException("You provided an incorrect password.");
        }

        return new UsernamePasswordAuthenticationToken(authentication.getName(), user.getPassword());
    }
}

