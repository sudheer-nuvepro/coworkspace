package com.nuvepro.coworkspacebooking.Service;

import com.nuvepro.coworkspacebooking.Entity.UserAuthentication;

public interface UserService {
    UserAuthentication getUser(Long id);
    UserAuthentication getUser(String username);
    UserAuthentication saveUser(UserAuthentication user);

}