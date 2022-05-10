package com.spbstu.StudMess.repository;

import com.spbstu.StudMess.model.MessageEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    Page<MessageEntity> findAllByChatId(@NonNull Long chatId, Pageable pageable);

    Page<MessageEntity> findAllBySenderId(@NonNull Long senderId, Pageable pageable);

    Page<MessageEntity> findAllBySenderIdAndChatId(@NonNull Long senderId, @NonNull Long chatId, Pageable pageable);

    Optional<MessageEntity> findByIdAndSenderIdAndChatId(@NonNull Long messageId, @NonNull Long senderId,
                                                         @NonNull Long chatId);
}
