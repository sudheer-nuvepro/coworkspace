package com.nuvepro.coworkspacebooking.Service;


import com.nuvepro.coworkspacebooking.Repository.CoWorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoworkspaceService {

    @Autowired
    CoWorkspaceRepository repo;

    public boolean checkIfCoworkSpaceExists(int coworkSpaceId){

        return repo.findById(coworkSpaceId).isPresent();
    }


}
