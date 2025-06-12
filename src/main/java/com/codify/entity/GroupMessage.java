package com.codify.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "group_messages")
@Check(constraints = "content IS NOT NULL OR file_id IS NOT NULL")
public class GroupMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_to_message_id")
    private GroupMessage replyToMessage;

    @OneToMany(mappedBy = "replyToMessage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GroupMessage> replies;

    @Column(name = "is_edited")
    private Boolean isEdited = false;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MessageReaction> reactions;

    public GroupMessage() {}

    public GroupMessage(ChatRoom room, User sender, String content) {
        this.room = room;
        this.sender = sender;
        this.content = content;
    }

    public GroupMessage(ChatRoom room, User sender, File file) {
        this.room = room;
        this.sender = sender;
        this.file = file;
    }

    public GroupMessage(ChatRoom room, User sender, String content, GroupMessage replyToMessage) {
        this.room = room;
        this.sender = sender;
        this.content = content;
        this.replyToMessage = replyToMessage;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public ChatRoom getRoom() {
        return room;
    }

    public void setRoom(ChatRoom room) {
        this.room = room;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public GroupMessage getReplyToMessage() {
        return replyToMessage;
    }

    public void setReplyToMessage(GroupMessage replyToMessage) {
        this.replyToMessage = replyToMessage;
    }

    public List<GroupMessage> getReplies() {
        return replies;
    }

    public void setReplies(List<GroupMessage> replies) {
        this.replies = replies;
    }

    public Boolean getIsEdited() {
        return isEdited;
    }

    public void setIsEdited(Boolean isEdited) {
        this.isEdited = isEdited;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<MessageReaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<MessageReaction> reactions) {
        this.reactions = reactions;
    }

    public boolean hasContent() {
        return content != null && !content.trim().isEmpty();
    }

    public boolean hasFile() {
        return file != null;
    }

    public boolean isReply() {
        return replyToMessage != null;
    }

    public void markAsEdited() {
        this.isEdited = true;
    }

    public void markAsDeleted() {
        this.isDeleted = true;
    }

    @Override
    public String toString() {
        return "GroupMessage{" +
                "messageId=" + messageId +
                ", sender=" + (sender != null ? sender.getNickname() : null) +
                ", content='" + (content != null ? content.substring(0, Math.min(50, content.length())) : null) + '\'' +
                ", hasFile=" + hasFile() +
                ", isReply=" + isReply() +
                ", createdAt=" + createdAt +
                '}';
    }
}