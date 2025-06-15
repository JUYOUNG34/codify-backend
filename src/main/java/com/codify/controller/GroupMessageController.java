package com.codify.controller;

import com.codify.entity.GroupMessage;
import com.codify.service.GroupMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/group-messages")
public class GroupMessageController {

    @Autowired
    private GroupMessageService groupMessageService;

    @PostMapping("/text")
    public ResponseEntity<?> sendTextMessage(@RequestBody Map<String, Object> request) {
        try {
            Long roomId = Long.valueOf(request.get("roomId").toString());
            Long senderId = Long.valueOf(request.get("senderId").toString());
            String content = request.get("content").toString();

            // 필수 파라미터 검증
            if (roomId == null || senderId == null || content == null || content.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "roomId, senderId, content는 모두 필수입니다."));
            }

            GroupMessage message = groupMessageService.sendTextMessage(roomId, senderId, content);

            return ResponseEntity.ok(Map.of(
                    "message", "메시지가 성공적으로 전송되었습니다.",
                    "data", message
            ));

        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "메시지 전송 중 오류가 발생했습니다."));
        }
    }
        @PostMapping("/file")
        public ResponseEntity<?> sendFileMessage(@RequestBody Map<String, Object> request) {
            try {
                Long roomId = Long.valueOf(request.get("roomId").toString());
                Long senderId = Long.valueOf(request.get("senderId").toString());
                Long fileId = Long.valueOf(request.get("fileId").toString());

                // 필수 파라미터 검증
                if (roomId == null || senderId == null || fileId == null) {
                    return ResponseEntity.badRequest()
                            .body(Map.of("error", "roomId, senderId, fileId는 모두 필수입니다."));
                }

                GroupMessage message = groupMessageService.sendFileMessage(roomId, senderId, fileId);

                return ResponseEntity.ok(Map.of(
                        "message", "파일이 성공적으로 전송되었습니다.",
                        "data", message
                ));

            } catch (IllegalArgumentException | IllegalStateException e) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", e.getMessage()));
            } catch (Exception e) {
                return ResponseEntity.internalServerError()
                        .body(Map.of("error", "파일 전송 중 오류가 발생했습니다."));
            }
        }
    @PostMapping("/reply")
    public ResponseEntity<?> sendReplyMessage(@RequestBody Map<String, Object> request) {
        try {
            Long roomId = Long.valueOf(request.get("roomId").toString());
            Long senderId = Long.valueOf(request.get("senderId").toString());
            String content = request.get("content").toString();
            Long replyToMessageId = Long.valueOf(request.get("replyToMessageId").toString());

            // 필수 파라미터 검증
            if (roomId == null || senderId == null || content == null ||
                    content.trim().isEmpty() || replyToMessageId == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "모든 필드가 필수입니다."));
            }

            GroupMessage replyMessage = groupMessageService.sendReplyMessage(
                    roomId, senderId, content, replyToMessageId);

            return ResponseEntity.ok(Map.of(
                    "message", "답글이 성공적으로 전송되었습니다.",
                    "data", replyMessage
            ));

        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "답글 전송 중 오류가 발생했습니다."));
        }
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<?> getRoomMessages(
            @PathVariable Long roomId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            // 페이지 크기 제한 (너무 많이 가져오는 것 방지)
            if (size > 100) {
                size = 100;
            }

            Page<GroupMessage> messages = groupMessageService.getRoomMessages(roomId, page, size);

            return ResponseEntity.ok(Map.of(
                    "roomId", roomId,
                    "messages", messages.getContent(),
                    "currentPage", page,
                    "pageSize", size,
                    "totalElements", messages.getTotalElements(),
                    "totalPages", messages.getTotalPages(),
                    "hasNext", messages.hasNext(),
                    "hasPrevious", messages.hasPrevious()
            ));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "메시지 조회 중 오류가 발생했습니다."));
        }
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<?> getMessage(@PathVariable Long messageId) {
        try {
            Optional<GroupMessage> message = groupMessageService.getMessage(messageId);

            if (message.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(Map.of(
                    "message", "메시지 조회 성공",
                    "data", message.get()
            ));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "메시지 조회 중 오류가 발생했습니다."));
        }
    }

    @PutMapping("/{messageId}")
    public ResponseEntity<?> editMessage(
            @PathVariable Long messageId,
            @RequestBody Map<String, Object> request) {
        try {
            Long senderId = Long.valueOf(request.get("senderId").toString());
            String newContent = request.get("content").toString();

            // 필수 파라미터 검증
            if (senderId == null || newContent == null || newContent.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "senderId와 content는 필수입니다."));
            }

            GroupMessage updatedMessage = groupMessageService.editMessage(messageId, senderId, newContent);

            return ResponseEntity.ok(Map.of(
                    "message", "메시지가 성공적으로 수정되었습니다.",
                    "data", updatedMessage
            ));

        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "메시지 수정 중 오류가 발생했습니다."));
        }
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<?> deleteMessage(
            @PathVariable Long messageId,
            @RequestBody Map<String, Object> request) {
        try {
            Long senderId = Long.valueOf(request.get("senderId").toString());

            // 필수 파라미터 검증
            if (senderId == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "senderId는 필수입니다."));
            }

            groupMessageService.deleteMessage(messageId, senderId);

            return ResponseEntity.ok(Map.of(
                    "message", "메시지가 성공적으로 삭제되었습니다."
            ));

        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "메시지 삭제 중 오류가 발생했습니다."));
        }
    }

    @GetMapping("/room/{roomId}/recent")
    public ResponseEntity<?> getRecentMessages(
            @PathVariable Long roomId,
            @RequestParam String since) {
        try {
            // ISO 8601 형식의 시간 문자열을 ZonedDateTime으로 변환
            ZonedDateTime sinceTime = ZonedDateTime.parse(since);

            List<GroupMessage> recentMessages = groupMessageService.getRecentMessages(roomId, sinceTime);

            return ResponseEntity.ok(Map.of(
                    "roomId", roomId,
                    "since", since,
                    "messages", recentMessages,
                    "count", recentMessages.size()
            ));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "올바른 시간 형식을 입력해주세요. (예: 2025-06-15T10:30:00Z)"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "최근 메시지 조회 중 오류가 발생했습니다."));
        }
    }

    @GetMapping("/room/{roomId}/stats")
    public ResponseEntity<?> getRoomStats(@PathVariable Long roomId) {
        try {
            // 첫 번째 페이지만 가져와서 전체 통계 정보 확인
            Page<GroupMessage> firstPage = groupMessageService.getRoomMessages(roomId, 0, 1);

            return ResponseEntity.ok(Map.of(
                    "roomId", roomId,
                    "totalMessages", firstPage.getTotalElements(),
                    "totalPages", firstPage.getTotalPages(),
                    "hasMessages", firstPage.getTotalElements() > 0
            ));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "통계 조회 중 오류가 발생했습니다."));
        }
    }


}
