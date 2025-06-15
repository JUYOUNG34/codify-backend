package com.codify.repository;

import com.codify.entity.ChatRoom;
import com.codify.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findByIsActiveTrue();

    List<ChatRoom> findByRoomTypeAndIsActiveTrue(RoomType roomType);

    List<ChatRoom> findByIsPublicTrueAndIsActiveTrue();
}