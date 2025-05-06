package com.nuvepro.coworkspacebooking.Repository;

import com.nuvepro.coworkspacebooking.Entity.CoWorkspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoWorkspaceRepository extends JpaRepository<CoWorkspace, Integer> {
    List<CoWorkspace> findByIsCabFacilityAvailableTrue();
    CoWorkspace findByWorkspaceId(Integer workspaceId);

    List<CoWorkspace> findByPincodeIgnoreCase(String pincode);

    List<CoWorkspace> findByIsCabFacilityAvailableTrueAndCity(String city);

    List<CoWorkspace> findByIsCabFacilityAvailableTrueAndCityContainsIgnoreCase(String city);


    List<CoWorkspace> findByCityIgnoreCase(String city);

    List<CoWorkspace> findByCoworkingSpaceNameIgnoreCase(String coworkingSpaceName);

    List<CoWorkspace> findByPincodeIgnoreCaseAndCityIgnoreCase(String pincode, String city);

    List<CoWorkspace> findByCityContainsIgnoreCase(String city);

    long countByCityContainsIgnoreCase(String city);


    long countByCityIgnoreCase(String city);

    List<CoWorkspace> findByCoworkingSpaceNameContainsIgnoreCase(String coworkingSpaceName);

    long countByIsCabFacilityAvailableTrue();

    long countByPincodeIgnoreCase(String pincode);









}
