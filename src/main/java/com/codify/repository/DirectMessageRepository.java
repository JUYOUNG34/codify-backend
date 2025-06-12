package com.codify.repository;

import com.codify.entity.DirectMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectMessageRepository {
    List<DirectMessage> findUnreadDirectMessages(@Param("userId") Long userId);
    Long countUnreadDirectMessages(@Param("userId") Long userId);
    List<DirectMessage> findDirectMessagesList(@Param("userId") Long userId);
    DirectMessage LastMessageBetweenUsers(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

    @Query("select dm from DirectMessage dm where (dm.sender.userId = :userId1 and dm.receiver.userId = :userId2) or (dm.sender.userId = :userId2 and dm.receiver.userId = :userId1) order by dm.createdAt desc")
    Page<DirectMessage> findBetweenUsersDirectMessage(@Param("userId1") Long userId1, @Param("userId2") Long userId2, Pageable pageable);

    @Query("SELECT DISTINCT CASE WHEN dm.sender.userId = :userId then dm.receiver else dm.sender end from DirectMessage dm where dm.sender.userId = :userId or dm.receiver.userId = :userId")
    List<Object> findDirectMessageUserList(@Param("userId") Long userId);

}
