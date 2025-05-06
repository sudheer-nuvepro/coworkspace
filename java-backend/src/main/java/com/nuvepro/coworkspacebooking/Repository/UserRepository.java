package com.nuvepro.coworkspacebooking.Repository;

import java.util.Optional;

import com.nuvepro.coworkspacebooking.Entity.UserAuthentication;
import org.springframework.data.repository.CrudRepository;



public interface UserRepository extends CrudRepository<UserAuthentication, Long> {
    Optional<UserAuthentication> findByUsername(String username);

    // boolean existsByUsername(String username);
}
