package com.nuvepro.coworkspacebooking.Service;

import com.nuvepro.coworkspacebooking.Entity.UserDetail;
import com.nuvepro.coworkspacebooking.Repository.UserDetailsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserDetailService {

    @Autowired
    private UserDetailsRepository repo;


    public void updateAuthenticationType(String username,String auth2Clientname){
//        System.out.println(username + " " + auth2Clientname);
        UserDetail.AuthenticationType authenticationType = UserDetail.AuthenticationType.valueOf(auth2Clientname.toUpperCase());
        repo.updateAuthenticationType(username,authenticationType);
    }

    public void processOAuthPostLogin(String username) {
        UserDetail existUser = repo.findByUserName(username);

        if (existUser == null) {
            UserDetail newUser = new UserDetail();
            newUser.setUserName(username);
            newUser.setUserType("USER");
//            newUser.setProvider(User.Provider.GOOGLE);
            newUser.setEnabled(true);

            repo.save(newUser);
        }
    }
}
