package com.codify.service;


import com.codify.entity.*;
import com.codify.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroupMessageService {

    @Autowired
    private GroupMessageRepository groupMessageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatParticipantRepository chatParticipantRepository;

    @Autowired
    private FileRepository fileRepository;

    public GroupMessage sendTextMessage(Long roomId, Long senderId, String content) {

        validateMessageSending(roomId , senderId);

        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("메세지 내용이 비어있습니다.");
        }

        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        GroupMessage groupMessage = new GroupMessage(chatRoom, sender, content.trim());
        return groupMessageRepository.save(groupMessage);

    }

    public GroupMessage sendFileMessage(Long roomId, Long senderId, Long fileId) {
        validateMessageSending(roomId, senderId);

        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("파일을 찾을 수 없습니다."));


        GroupMessage message = new GroupMessage(chatRoom, sender, file);
        return groupMessageRepository.save(message);
    }


    public GroupMessage sendReplyMessage(Long roomId, Long senderId, String content, Long replyToMessageId) {
        validateMessageSending(roomId, senderId);

        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("메시지 내용이 비어있습니다.");
        }

        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        GroupMessage replyToMessage = groupMessageRepository.findById(replyToMessageId)
                .orElseThrow(() -> new IllegalArgumentException("답글 대상 메시지를 찾을 수 없습니다."));

        if (!replyToMessage.getRoom().getRoomId().equals(roomId)) {
            throw new IllegalArgumentException("다른 채팅방의 메시지에는 답글을 달 수 없습니다.");
        }

        GroupMessage replyMessage = new GroupMessage(chatRoom, sender, content.trim(), replyToMessage);
        return groupMessageRepository.save(replyMessage);
    }


    public Page<GroupMessage> getRoomMessages(Long roomId, int page, int size) {
        if (!chatRoomRepository.existsById(roomId)) {
            throw new IllegalArgumentException("채팅방을 찾을 수 없습니다.");
        }

        Pageable pageable = PageRequest.of(page, size);

        return groupMessageRepository.findByRoomIdOrderByCreatedAt(roomId, pageable);
    }


    public GroupMessage editMessage(Long messageId, Long senderId, String newContent) {
        GroupMessage message = groupMessageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("메시지를 찾을 수 없습니다."));

        if (!message.getSender().getUserId().equals(senderId)) {
            throw new IllegalArgumentException("본인이 작성한 메시지만 수정할 수 있습니다.");
        }

        if (message.getIsDeleted()) {
            throw new IllegalArgumentException("삭제된 메시지는 수정할 수 없습니다.");
        }

        if (message.hasFile() && !message.hasContent()) {
            throw new IllegalArgumentException("파일 메시지는 수정할 수 없습니다.");
        }

        if (newContent == null || newContent.trim().isEmpty()) {
            throw new IllegalArgumentException("메시지 내용이 비어있습니다.");
        }


        message.setContent(newContent.trim());
        message.markAsEdited();

        return groupMessageRepository.save(message);
    }


    public void deleteMessage(Long messageId, Long senderId) {
        GroupMessage message = groupMessageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("메시지를 찾을 수 없습니다."));


        if (!message.getSender().getUserId().equals(senderId)) {
            throw new IllegalArgumentException("본인이 작성한 메시지만 삭제할 수 있습니다.");
        }

        if (message.getIsDeleted()) {
            throw new IllegalArgumentException("이미 삭제된 메시지입니다.");
        }


        message.markAsDeleted();
        groupMessageRepository.save(message);
    }


    public Optional<GroupMessage> getMessage(Long messageId) {
        Optional<GroupMessage> message = groupMessageRepository.findById(messageId);


        if (message.isPresent() && message.get().getIsDeleted()) {
            return Optional.empty();
        }

        return message;
    }


    public List<GroupMessage> getRecentMessages(Long roomId, ZonedDateTime since) {
        return groupMessageRepository.findRecentMessages(roomId, since);
    }


    private void validateMessageSending(Long roomId, Long senderId) {

        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));

        if (!chatRoom.getIsActive()) {
            throw new IllegalStateException("비활성화된 채팅방입니다.");
        }

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!sender.getIsActive()) {
            throw new IllegalStateException("비활성화된 사용자입니다.");
        }

        Optional<ChatParticipant> participant =
                chatParticipantRepository.findByRoomIdAndUserId(roomId, senderId);

        if (participant.isEmpty() || !participant.get().isActive()) {
            throw new IllegalStateException("채팅방에 참여하지 않은 사용자입니다.");
        }
    }
}


