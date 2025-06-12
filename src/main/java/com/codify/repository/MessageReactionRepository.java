package com.codify.repository;

import com.codify.entity.MessageReaction;
import com.codify.entity.ReactionType;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageReactionRepository {

    List<MessageReaction> findByMessageId(@Param("messageId") Long messageId);
    List<MessageReaction> findByMessageIdAndReactionType(@Param("messageId") Long messageId, @Param("reactionType") ReactionType reactionType);
    List<Object[]> getReactionStatsByMessageId(@Param("messageId") Long messageId);
    List<MessageReaction> findByMessageIdAndUserId(@Param("messageId") Long messageId, @Param("userId") Long userId);


}
