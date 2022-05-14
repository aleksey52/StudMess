package com.spbstu.StudMess.service;

import com.spbstu.StudMess.exception.NotFoundException;
import com.spbstu.StudMess.model.ChatEntity;
import com.spbstu.StudMess.model.MessageEntity;
import com.spbstu.StudMess.model.UserEntity;
import com.spbstu.StudMess.repository.MessageRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatService chatService;
    private final UserService userService;

    @NonNull
    @Transactional
    public MessageEntity create(@NonNull Long senderId, @NonNull Long chatId, @NonNull String content, Long recipientId) {
        final ChatEntity chatEntity = chatService.findById(chatId);
        final UserEntity sender = userService.findById(senderId);
        final MessageEntity messageEntity = new MessageEntity(content, sender, chatEntity);

        if (recipientId != null) {
            final UserEntity recipient = userService.findById(recipientId);
            messageEntity.setRecipient(recipient);
        }

        return messageRepository.save(messageEntity);
    }

    @NonNull
    @Transactional(readOnly = true)
    public MessageEntity findByIdAndSenderIdAndChatId(@NonNull Long id, @NonNull Long senderId, @NonNull Long chatId) {
        return messageRepository.findByIdAndSenderIdAndChatId(id, senderId, chatId).orElseThrow(() ->
                new NotFoundException(MessageEntity.class, id.toString()));
    }

    @Transactional(readOnly = true)
    public MessageEntity findLastByChatId(@NonNull Long chatId) {
        return messageRepository.findFirstByChatIdOrderByCreationDateDesc(chatId).orElseThrow(() ->
                new NotFoundException(MessageEntity.class, "chatId", chatId.toString()));
    }

    @Transactional(readOnly = true)
    public Page<MessageEntity> findAllByChatId(@NonNull Long chatId, Pageable pageable) {
        return messageRepository.findAllByChatId(chatId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MessageEntity> findAllBySenderId(@NonNull Long senderId, Pageable pageable) {
        return messageRepository.findAllBySenderId(senderId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MessageEntity> findAllBySenderIdAndChatId(@NonNull Long senderId, @NonNull Long chatId, Pageable pageable) {
        return messageRepository.findAllBySenderIdAndChatId(senderId, chatId, pageable);
    }

    @NonNull
    @Transactional
    public MessageEntity update(@NonNull Long id, @NonNull Long senderId, @NonNull Long chatId, @NonNull String content,
                                Long recipientId) {
        final MessageEntity messageEntity = messageRepository.findByIdAndSenderIdAndChatId(id, senderId, chatId)
                .orElseThrow(() -> new NotFoundException(MessageEntity.class, id.toString()));
        final UserEntity recipient = userService.findById(recipientId);

        messageEntity.setRecipient(recipient);
        messageEntity.setContent(content);
        messageEntity.setUpdateDate(LocalDateTime.now());

        return messageRepository.save(messageEntity);
    }

    @Transactional
    public void delete(@NonNull Long id, @NonNull Long senderId, @NonNull Long chatId) {
        final MessageEntity messageEntity = messageRepository.findByIdAndSenderIdAndChatId(id, senderId, chatId)
                .orElseThrow(() -> new NotFoundException(MessageEntity.class, id.toString()));
        messageRepository.delete(messageEntity);
    }
}
