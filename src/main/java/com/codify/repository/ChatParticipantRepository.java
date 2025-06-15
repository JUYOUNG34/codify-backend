package com.codify.repository;

import com.codify.entity.ChatParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {

    @Query("SELECT cp FROM ChatParticipant cp WHERE cp.room.roomId = :roomId AND cp.leftAt IS NULL")
    List<ChatParticipant> findByRoomId(@Param("roomId") Long roomId);

    @Query("SELECT cp FROM ChatParticipant cp WHERE cp.user.userId = :userId AND cp.leftAt IS NULL")
    List<ChatParticipant> findActiveRoomsByUserId(@Param("userId") Long userId);

    @Query("SELECT cp FROM ChatParticipant cp WHERE cp.room.roomId = :roomId AND cp.user.userId = :userId")
    Optional<ChatParticipant> findByRoomIdAndUserId(@Param("roomId") Long roomId, @Param("userId") Long userId);
}