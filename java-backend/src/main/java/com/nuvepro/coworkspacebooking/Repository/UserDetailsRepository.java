package com.nuvepro.coworkspacebooking.Repository;

import com.nuvepro.coworkspacebooking.Entity.UserDetail;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends CrudRepository<UserDetail,Integer> {

     UserDetail findByUserName(String userName);

     @Modifying
     @Query("UPDATE UserDetail u SET u.authType = ?2 WHERE u.userName = ?1")
     public void updateAuthenticationType(String username, UserDetail.AuthenticationType authType);



}
