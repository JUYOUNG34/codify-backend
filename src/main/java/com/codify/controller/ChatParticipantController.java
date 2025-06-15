package com.codify.controller;


import com.codify.entity.ChatParticipant;
import com.codify.service.ChatParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat-participants")
public class ChatParticipantController {

    @Autowired
    private ChatParticipantService chatParticipantService;

    @PostMapping("/join")
    public ResponseEntity<?> joinRoom(@RequestBody Map<String, Long> request) {
        try {
            Long roomId = request.get("roomId");
            Long userId = request.get("userId");

            // 필수 파라미터 검증
            if (roomId == null || userId == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "roomId와 userId는 필수입니다."));
            }

            ChatParticipant participant = chatParticipantService.joinRoom(roomId, userId);

            return ResponseEntity.ok(Map.of(
                    "message", "채팅방에 성공적으로 입장했습니다.",
                    "participant", participant
            ));

        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "채팅방 입장 중 오류가 발생했습니다."));
        }
    }

    @PostMapping("/leave")
    public ResponseEntity<?> leaveRoom(@RequestBody Map<String, Long> request) {
        try {
            Long roomId = request.get("roomId");
            Long userId = request.get("userId");

            // 필수 파라미터 검증
            if (roomId == null || userId == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "roomId와 userId는 필수입니다."));
            }

            chatParticipantService.leaveRoom(roomId, userId);

            return ResponseEntity.ok(Map.of(
                    "message", "채팅방에서 성공적으로 퇴장했습니다."
            ));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "채팅방 퇴장 중 오류가 발생했습니다."));
        }
    }

    @GetMapping("/user/{userId}/rooms")
    public ResponseEntity<?> getUserRooms(@PathVariable Long userId) {
        try {
            List<ChatParticipant> participants = chatParticipantService.getUserParticipants(userId);

            return ResponseEntity.ok(Map.of(
                    "userId", userId,
                    "joinedRooms", participants,
                    "roomCount", participants.size()
            ));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "사용자 채팅방 목록 조회 중 오류가 발생했습니다."));
        }
    }

    @PutMapping("/read-message")
    public ResponseEntity<?> updateLastReadMessage(@RequestBody Map<String, Long> request) {
        try {
            Long roomId = request.get("roomId");
            Long userId = request.get("userId");
            Long messageId = request.get("messageId");

            // 필수 파라미터 검증
            if (roomId == null || userId == null || messageId == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "roomId, userId, messageId는 모두 필수입니다."));
            }

            chatParticipantService.updateLastReadMessage(roomId, userId, messageId);

            return ResponseEntity.ok(Map.of(
                    "message", "읽음 상태가 업데이트되었습니다.",
                    "roomId", roomId,
                    "userId", userId,
                    "lastReadMessageId", messageId
            ));

        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "읽음 상태 업데이트 중 오류가 발생했습니다."));
        }
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkParticipation(
            @RequestParam Long roomId,
            @RequestParam Long userId) {
        try {
            // 참여 여부 확인을 위한 간단한 조회
            List<ChatParticipant> userRooms = chatParticipantService.getUserParticipants(userId);
            boolean isParticipating = userRooms.stream()
                    .anyMatch(p -> p.getRoom().getRoomId().equals(roomId));

            return ResponseEntity.ok(Map.of(
                    "roomId", roomId,
                    "userId", userId,
                    "isParticipating", isParticipating
            ));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "참여 상태 확인 중 오류가 발생했습니다."));
        }
    }
    }



