package com.codify.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "chat_rooms")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // 이 줄 추가
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @NotBlank
    @Size(max = 100)
    @Column(name = "room_name", nullable = false)
    private String roomName;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private RoomType roomType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tech_category_id")
    private TechCategory techCategory;

    @Column(name = "max_participants")
    private Integer maxParticipants = 100;

    @Column(name = "current_participants")
    private Integer currentParticipants = 0;

    @Column(name = "is_public")
    private Boolean isPublic = true;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatParticipant> participants;

    @JsonIgnore
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GroupMessage> messages;

    public ChatRoom() {}

    public ChatRoom(String roomName, RoomType roomType, User createdBy) {
        this.roomName = roomName;
        this.roomType = roomType;
        this.createdBy = createdBy;
    }

    public ChatRoom(String roomName, RoomType roomType, TechCategory techCategory, User createdBy) {
        this.roomName = roomName;
        this.roomType = roomType;
        this.techCategory = techCategory;
        this.createdBy = createdBy;
    }

    // getters and setters (기존과 동일)
    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TechCategory getTechCategory() {
        return techCategory;
    }

    public void setTechCategory(TechCategory techCategory) {
        this.techCategory = techCategory;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Integer getCurrentParticipants() {
        return currentParticipants;
    }

    public void setCurrentParticipants(Integer currentParticipants) {
        this.currentParticipants = currentParticipants;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<ChatParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ChatParticipant> participants) {
        this.participants = participants;
    }

    public List<GroupMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<GroupMessage> messages) {
        this.messages = messages;
    }

    public boolean isFull() {
        return currentParticipants >= maxParticipants;
    }

    public void incrementParticipants() {
        this.currentParticipants++;
    }

    public void decrementParticipants() {
        if (this.currentParticipants > 0) {
            this.currentParticipants--;
        }
    }

    @Override
    public String toString() {
        return "ChatRoom{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", roomType=" + roomType +
                ", currentParticipants=" + currentParticipants +
                ", maxParticipants=" + maxParticipants +
                ", isActive=" + isActive +
                '}';
    }
}