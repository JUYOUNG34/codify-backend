package com.codify.repository;

import com.codify.entity.ChatParticipant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatParticipantRepository {

    @Query("SELECT cp FROM ChatParticipant cp WHERE cp.room.roomId = :roomId AND cp.leftAt IS NULL")
    List<ChatParticipant> findByRoomId(@Param("roomId") Long roomId);

    @Query("select cp from ChatParticipant cp where cp.user.userId = :userId and cp.leftAt is null")
    List<ChatParticipant> findActiveRoomsByUserId(@Param("userId") Long userId);

    Long countActiveParticipants(@Param("roomId") Long roomId);
    List<ChatParticipant> findRecentActiveRooms(@Param("userId") Long userId);
    boolean existsActiveParticipants(Long roomId, Long userId);
}
