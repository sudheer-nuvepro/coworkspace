package com.nuvepro.coworkspacebooking.Service;

import static org.assertj.core.api.Assertions.assertThat;


import com.nuvepro.coworkspacebooking.Entity.UserDetail;
import com.nuvepro.coworkspacebooking.Repository.UserDetailsRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.test.annotation.Rollback;

@DataJpaTest

@AutoConfigureTestDatabase(replace = Replace.NONE)

@Rollback(false)

public class UserRepositoryTests {
//
//    @Autowired
//
//    private TestEntityManager entityManager;
//
//    @Autowired
//
//    private UserDetailsRepository repo;
//
//    @Test
//    public void testCreateUser() {
//        UserDetail user = new UserDetail();
//        user.setPassword("ravi2020");
//        user.setUserName("Ravi");
//        user.setUserType("Admin");
//        UserDetail savedUser = repo.save(user);
//
//
//        UserDetail existUser = entityManager.find(UserDetail.class, savedUser.getUserName());
//        assertThat(user.getUserName()).isEqualTo(existUser.getUserName());
//
//    }
}