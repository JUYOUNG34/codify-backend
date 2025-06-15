package com.codify.controller;

import com.codify.dto.ChatRoomRequest;
import com.codify.entity.ChatRoom;
import com.codify.entity.RoomType;
import com.codify.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chat-rooms")
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;

    // 모든 활성 채팅방 조회
    @GetMapping
    public ResponseEntity<List<ChatRoom>> getActiveRooms() {
        List<ChatRoom> rooms = chatRoomService.getActiveRooms();
        return ResponseEntity.ok(rooms);
    }

    // 특정 채팅방 조회
    @GetMapping("/{roomId}")
    public ResponseEntity<ChatRoom> getRoomById(@PathVariable Long roomId) {
        Optional<ChatRoom> room = chatRoomService.getRoomById(roomId);
        return room.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // 타입별 채팅방 조회
    @GetMapping("/type/{roomType}")
    public ResponseEntity<List<ChatRoom>> getRoomsByType(@PathVariable RoomType roomType) {
        List<ChatRoom> rooms = chatRoomService.getRoomsByType(roomType);
        return ResponseEntity.ok(rooms);
    }

    // 채팅방 생성
    @PostMapping
    public ResponseEntity<?> createChatRoom(@RequestBody ChatRoomRequest request) {
        try {
            ChatRoom room = chatRoomService.createChatRoom(
                    request.getRoomName(),
                    request.getRoomType(),
                    request.getDescription(),
                    request.getTechCategoryId(),
                    request.getMaxParticipants(),
                    request.getIsPublic(),
                    request.getCreatedByUserId()
            );
            return ResponseEntity.ok(room);
        } catch (IllegalArgumentException e) {
            // 에러 메시지를 JSON으로 반환
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            // 기타 에러
            return ResponseEntity.badRequest().body("{\"error\": \"채팅방 생성 중 오류가 발생했습니다: " + e.getMessage() + "\"}");
        }
    }
}