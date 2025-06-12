package com.codify.repository;

import com.codify.entity.Notification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository {

    List<Notification> findByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId);
    List<Notification> findUnreadUserId(@Param("userId") Long userId);
    Long countUnreadByUserID(@Param("userId") Long userId);
    List<Notification> findByUserIdAndType(@Param("userId") Long userId, @Param("type") Notification type);
}
