package com.nuvepro.coworkspacebooking.Repository;

import com.nuvepro.coworkspacebooking.Entity.CoWorkspace;
import com.nuvepro.coworkspacebooking.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Integer> {
    Room findByRoomIdAndAvailableTrue(int roomId);

//    List<Room> findByRoomIdAndAvailableTrue(int roomId);

    List<Room> findByAvailableTrue();

    List<Room> findByWorkspaceAndAvailableTrue(CoWorkspace workspace);

    Room findByRoomId(int roomId);

}
