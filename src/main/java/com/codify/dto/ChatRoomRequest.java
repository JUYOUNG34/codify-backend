package com.codify.dto;

import com.codify.entity.RoomType;

public class ChatRoomRequest {
    private String roomName;
    private RoomType roomType;
    private String description;
    private Long techCategoryId;
    private Integer maxParticipants;
    private Boolean isPublic;
    private Long createdByUserId;

    public ChatRoomRequest() {}

    public ChatRoomRequest(String roomName, RoomType roomType, String description,
                                 Long techCategoryId, Integer maxParticipants, Boolean isPublic,
                                 Long createdByUserId) {
        this.roomName = roomName;
        this.roomType = roomType;
        this.description = description;
        this.techCategoryId = techCategoryId;
        this.maxParticipants = maxParticipants;
        this.isPublic = isPublic;
        this.createdByUserId = createdByUserId;
    }

    // getters and setters
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

    public Long getTechCategoryId() {
        return techCategoryId;
    }

    public void setTechCategoryId(Long techCategoryId) {
        this.techCategoryId = techCategoryId;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }
}