package com.nuvepro.coworkspacebooking.Service;

import com.nuvepro.coworkspacebooking.Entity.UserAuthentication;
import com.nuvepro.coworkspacebooking.Repository.UserRepository;
import com.nuvepro.coworkspacebooking.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserAuthentication getUser(Long id) {
        Optional<UserAuthentication> user = userRepository.findById(id);
        return unwrapUser(user, id);
    }

    @Override
    public UserAuthentication getUser(String username) {
        Optional<UserAuthentication> user = userRepository.findByUsername(username);
        return unwrapUser(user, 404L);
    }

    @Override
    public UserAuthentication saveUser(UserAuthentication user) {
        System.out.println("Helooooooooooo");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    static UserAuthentication unwrapUser(Optional<UserAuthentication> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, UserAuthentication.class);
    }


}
