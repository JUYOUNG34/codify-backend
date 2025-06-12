package com.codify.repository;


import com.codify.entity.ChatRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository {

    List<ChatRoom> findByIsActiveTrue();
    List<ChatRoom> findByRoomTypeAndIsActiveTrue(String roomType);
    List<ChatRoom> findByIsPublicTrueAndIsActiveTrue();
    List<ChatRoom> findByCreatedByUserId(@Param("userId") Long userId);
    List<ChatRoom> findByOrderByCurrentParticipantDesc();



    @Query("select cr from ChatRoom cr where cr.roomName like %:keyword% and cr.isActive = true")
    List<ChatRoom> SearchByRoomName(@Param("keyword") String keyword);

    @Query("select cr from ChatRoom cr where cr.techCategory.categoryId = :categoryId and cr.isActive = true")
    List<ChatRoom> SearchByCategoryId(@Param("categoryId") int categoryId);

    @Query("select cr from  ChatRoom cr where cr.currentParticipants < cr.currentParticipants and cr.isPublic and cr.isActive = true")
    List<ChatRoom> findAvailableRooms();
}
