package com.codify.repository;

import com.codify.entity.GroupMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface GroupMessageRepository extends JpaRepository<GroupMessage, Long> {

    @Query("SELECT gm FROM GroupMessage gm WHERE gm.room.roomId = :roomId AND gm.isDeleted = false ORDER BY gm.createdAt DESC")
    Page<GroupMessage> findByRoomIdOrderByCreatedAt(@Param("roomId") Long roomId, Pageable pageable);

    @Query("SELECT gm FROM GroupMessage gm WHERE gm.room.roomId = :roomId AND gm.createdAt > :since AND gm.isDeleted = false ORDER BY gm.createdAt ASC")
    List<GroupMessage> findRecentMessages(@Param("roomId") Long roomId, @Param("since") ZonedDateTime since);
}