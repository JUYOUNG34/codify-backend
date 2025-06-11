package com.codify.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "chat_participants",
        uniqueConstraints = @UniqueConstraint(columnNames = {"room_id", "user_id"}))
public class ChatParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Long participantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "last_read_message_id")
    private Long lastReadMessageId;

    @CreationTimestamp
    @Column(name = "joined_at", nullable = false, updatable = false)
    private ZonedDateTime joinedAt;

    @Column(name = "left_at")
    private ZonedDateTime leftAt;

    public ChatParticipant() {}

    public ChatParticipant(ChatRoom room, User user) {
        this.room = room;
        this.user = user;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public ChatRoom getRoom() {
        return room;
    }

    public void setRoom(ChatRoom room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getLastReadMessageId() {
        return lastReadMessageId;
    }

    public void setLastReadMessageId(Long lastReadMessageId) {
        this.lastReadMessageId = lastReadMessageId;
    }

    public ZonedDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(ZonedDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public ZonedDateTime getLeftAt() {
        return leftAt;
    }

    public void setLeftAt(ZonedDateTime leftAt) {
        this.leftAt = leftAt;
    }

    public boolean isActive() {
        return leftAt == null;
    }

    public void leave() {
        this.leftAt = ZonedDateTime.now();
    }

    @Override
    public String toString() {
        return "ChatParticipant{" +
                "participantId=" + participantId +
                ", room=" + (room != null ? room.getRoomName() : null) +
                ", user=" + (user != null ? user.getNickname() : null) +
                ", isActive=" + isActive() +
                '}';
    }
}