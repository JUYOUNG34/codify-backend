package com.codify.service;

import com.codify.entity.*;
import com.codify.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatParticipantRepository chatParticipantRepository;

    @Autowired
    private TechCategoryRepository techCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    // 채팅방 생성
    public ChatRoom createChatRoom(String roomName, RoomType roomType, String description,
                                   Long techCategoryId, Integer maxParticipants,
                                   Boolean isPublic, Long createdByUserId) {
        User creator = userRepository.findById(createdByUserId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        ChatRoom chatRoom = new ChatRoom(roomName, roomType, creator);
        chatRoom.setDescription(description);
        chatRoom.setMaxParticipants(maxParticipants != null ? maxParticipants : 100);
        chatRoom.setIsPublic(isPublic != null ? isPublic : true);

        // 기술 카테고리 설정
        if (techCategoryId != null) {
            TechCategory techCategory = techCategoryRepository.findById(techCategoryId)
                    .orElseThrow(() -> new IllegalArgumentException("기술 카테고리를 찾을 수 없습니다."));
            chatRoom.setTechCategory(techCategory);
        }

        return chatRoomRepository.save(chatRoom);
    }

    // 모든 활성 채팅방 조회
    public List<ChatRoom> getActiveRooms() {
        return chatRoomRepository.findByIsActiveTrue();
    }

    // 특정 채팅방 조회
    public Optional<ChatRoom> getRoomById(Long roomId) {
        return chatRoomRepository.findById(roomId);
    }

    // 타입별 채팅방 조회
    public List<ChatRoom> getRoomsByType(RoomType roomType) {
        return chatRoomRepository.findByRoomTypeAndIsActiveTrue(roomType);
    }
}