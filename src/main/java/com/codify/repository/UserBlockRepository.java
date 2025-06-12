package com.codify.repository;

import com.codify.entity.UserBlock;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBlockRepository {
    List<UserBlock> findByBlockerId(@Param("blockedId") Long blockedId);
    List<UserBlock> findByBlockedId(@Param("blockedId") Long blockedId);
    Optional<UserBlock> findByBlockerIdAndBlockedId(@Param("blockedId") Long blockedId, @Param("blockerId") Long blockerId);
    boolean existByBlockerUserIAndBlockedUserId(Long blockedId, Long blockerId);
}
