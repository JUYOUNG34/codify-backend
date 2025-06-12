package com.codify.repository;

import com.codify.entity.GroupMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface GroupMessageRepository {

    @Query("select gm from GroupMessage gm where gm.room.roomId = :roomId and gm.isDeleted = false order by gm.createdAt desc")
    Page<GroupMessage> findByRoomIdOrderByCreatedAt(@Param("roomId") Long roomId, Pageable pageable);

    List<GroupMessage> findBySenderUserId(@Param("userId") Long userId);
    List<GroupMessage> findMessageWithFiles(@Param("roomId") Long roomId);
    List<GroupMessage> findRepliesByMessageId(@Param("messageId") Long messageId);
    List<GroupMessage> SearchInRoom(@Param("roomId") Long roomId , @Param("keyword") String keyword);

    // 최근 메시지들
    @Query("SELECT gm FROM GroupMessage gm WHERE gm.room.roomId = :roomId AND gm.createdAt > :since AND gm.isDeleted = false ORDER BY gm.createdAt ASC")
    List<GroupMessage> findRecentMessages(@Param("roomId") Long roomId, @Param("since") ZonedDateTime since);

    @Query("SELECT COUNT(gm) FROM GroupMessage gm WHERE gm.room.roomId = :roomId AND gm.createdAt > :since AND gm.sender.userId != :excludeUserId AND gm.isDeleted = false")
    Long countUnreadMessages(@Param("roomId") Long roomId, @Param("since") ZonedDateTime since, @Param("excludeUserId") Long excludeUserId);

}
