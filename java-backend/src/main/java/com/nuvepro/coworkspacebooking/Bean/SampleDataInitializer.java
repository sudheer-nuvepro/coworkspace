package com.nuvepro.coworkspacebooking.Bean;

import com.nuvepro.coworkspacebooking.Entity.CoWorkspace;
import com.nuvepro.coworkspacebooking.Repository.CoWorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.util.List;

@Component
public class SampleDataInitializer {

    private final CoWorkspaceRepository coWorkspaceRepository;

    @Autowired
    public SampleDataInitializer(CoWorkspaceRepository coWorkspaceRepository) {
        this.coWorkspaceRepository = coWorkspaceRepository;
    }

    @PostConstruct
    public void init() {
        addSampleCoWorkspaces();
    }

    private void addSampleCoWorkspaces() {
        CoWorkspace workspace1 = new CoWorkspace();
        workspace1.setCoworkingSpaceName("Workspace 1");
        workspace1.setTotalCapacity(50);
        workspace1.setPhoneNumber("1234567890");
        workspace1.setCompanyEmail("workspace1@example.com");
        workspace1.setAddress("123 Street, Sample City");
        workspace1.setCity("Sample City");
        workspace1.setState("Sample State");
        workspace1.setCabFacilityAvailable(true);
        workspace1.setPincode("12345");
        workspace1.setRating(4.5f);

        CoWorkspace workspace2 = new CoWorkspace();
        workspace2.setCoworkingSpaceName("Workspace 2");
        workspace2.setTotalCapacity(30);
        workspace2.setPhoneNumber("9876543210");
        workspace2.setCompanyEmail("workspace2@example.com");
        workspace2.setAddress("456 Avenue, Another City");
        workspace2.setCity("Another City");
        workspace2.setState("Another State");
        workspace2.setCabFacilityAvailable(false);
        workspace2.setPincode("54321");
        workspace2.setRating(3.8f);

        CoWorkspace workspace3 = new CoWorkspace();
        workspace3.setCoworkingSpaceName("Workspace 3");
        workspace3.setTotalCapacity(20);
        workspace3.setPhoneNumber("5555555555");
        workspace3.setCompanyEmail("workspace3@example.com");
        workspace3.setAddress("789 Boulevard, Third City");
        workspace3.setCity("Third City");
        workspace3.setState("Third State");
        workspace3.setCabFacilityAvailable(true);
        workspace3.setPincode("67890");
        workspace3.setRating(4.2f);

        coWorkspaceRepository.saveAll(List.of(workspace1, workspace2, workspace3));
    }
}