package com.codify.service;

import com.codify.entity.ChatParticipant;
import com.codify.entity.ChatRoom;
import com.codify.entity.User;
import com.codify.repository.ChatParticipantRepository;
import com.codify.repository.ChatRoomRepository;
import com.codify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ChatParticipantService {

    @Autowired
    private ChatParticipantRepository chatParticipantRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    public ChatParticipant joinRoom(Long roomId, Long userId) {

        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));

        if (!chatRoom.getIsActive()) {
            throw new IllegalStateException("비활성화된 채팅방입니다.");
        }

        Optional<ChatParticipant> existingParticipant = chatParticipantRepository.findByRoomIdAndUserId(roomId, userId);

        if (existingParticipant.isPresent() && existingParticipant.get().isActive()) {
            throw new IllegalStateException("이미 참여 중인 채팅방입니다.");
        }
        if (chatRoom.isFull()) {
            throw new IllegalStateException("채팅방이 가득 찼네용.");
        }

        ChatParticipant participant;
        if (existingParticipant.isPresent()) {
            participant = existingParticipant.get();
            participant.setLeftAt(null);
        } else  {
            participant = new ChatParticipant(chatRoom, user);
        }

        chatRoom.incrementParticipants();

        chatRoomRepository.save(chatRoom);
        return chatParticipantRepository.save(participant);

    }

    public void leaveRoom(Long roomId, Long userId) {
        ChatParticipant participant = chatParticipantRepository
                .findByRoomIdAndUserId(roomId, userId)
                .orElseThrow(() -> new IllegalArgumentException("참여하지 않은 채팅방입니다."));

        participant.leave();

        ChatRoom chatRoom = participant.getRoom();
        chatRoom.decrementParticipants();

        chatRoomRepository.save(chatRoom);
        chatParticipantRepository.save(participant);
    }

    public List<ChatParticipant> getUserParticipants(Long userId) {
        return chatParticipantRepository.findActiveRoomsByUserId(userId);
    }

    public void updateLastReadMessage(Long roomId, Long userId , Long messageId) {
        ChatParticipant participant = chatParticipantRepository
                .findByRoomIdAndUserId(roomId, userId)
                .orElseThrow(() -> new IllegalArgumentException("참여하지 않은 채팅방입니다."));

        if (!participant.isActive()) {
            throw new IllegalStateException("참여하지 않은 채팅방입니다.");
        }

        participant.setLastReadMessageId(messageId);
        chatParticipantRepository.save(participant);
    }
}
