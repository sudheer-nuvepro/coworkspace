package com.nuvepro.coworkspacebooking.Service;

import com.nuvepro.coworkspacebooking.Entity.UserDetail;
import com.nuvepro.coworkspacebooking.Repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Attempting to authenticate user: " + username);
        UserDetail employee = userDetailsRepository.findByUserName(username);
        if (employee == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (employee.getUserType().equalsIgnoreCase("ADMIN")) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }
        else if(employee.getUserType().equalsIgnoreCase("USER")){
            authorities.add(new SimpleGrantedAuthority("USER"));
        }
        System.out.println("UserType: " + employee.getUserType());
        org.springframework.security.core.userdetails.UserDetails userDetails = new User(employee.getUserName(), employee.getPassword(), authorities);
        System.out.println("User " + username + " successfully authenticated");
        return userDetails;
    }


}
