package com.spbstu.StudMess.service;

import com.spbstu.StudMess.exception.NotFoundException;
import com.spbstu.StudMess.model.CommentEntity;
import com.spbstu.StudMess.model.TaskEntity;
import com.spbstu.StudMess.model.UserEntity;
import com.spbstu.StudMess.repository.CommentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskService taskService;
    private final UserService userService;

    @NonNull
    @Transactional
    public CommentEntity create(@NonNull Long senderId, @NonNull Long taskId, @NonNull String content, Long recipientId) {
        final TaskEntity taskEntity = taskService.findById(taskId);
        final UserEntity sender = userService.findById(senderId);
        final CommentEntity commentEntity = new CommentEntity(content, sender, taskEntity);

        if (recipientId != null) {
            final UserEntity recipient = userService.findById(recipientId);
            commentEntity.setRecipient(recipient);
        }

        return commentRepository.save(commentEntity);
    }

    @NonNull
    @Transactional(readOnly = true)
    public CommentEntity findByIdAndSenderIdAndChatId(@NonNull Long id, @NonNull Long senderId, @NonNull Long taskId) {
        return commentRepository.findByIdAndSenderIdAndTaskId(id, senderId, taskId).orElseThrow(() ->
                new NotFoundException(CommentEntity.class, id.toString()));
    }

    @NonNull
    @Transactional(readOnly = true)
    Page<CommentEntity> findAllByTaskId(@NonNull Long taskId, Pageable pageable) {
        return commentRepository.findAllByTaskId(taskId, pageable);
    }

    Page<CommentEntity> findAllBySenderId(@NonNull Long senderId, Pageable pageable) {
        return commentRepository.findAllBySenderId(senderId, pageable);
    }

    Page<CommentEntity> findAllBySenderIdAndTaskId(@NonNull Long senderId, @NonNull Long taskId, Pageable pageable) {
        return commentRepository.findAllBySenderIdAndTaskId(senderId, taskId, pageable);
    }

    @NonNull
    @Transactional
    public CommentEntity update(@NonNull Long id, @NonNull Long senderId, @NonNull Long taskId, @NonNull String content) {
        final CommentEntity commentEntity = commentRepository.findByIdAndSenderIdAndTaskId(id, senderId, taskId)
                .orElseThrow(() -> new NotFoundException(CommentEntity.class, id.toString()));
        commentEntity.setContent(content);

        return commentRepository.save(commentEntity);
    }

    @Transactional
    public void delete(@NonNull Long id, @NonNull Long senderId, @NonNull Long taskId) {
        final CommentEntity commentEntity = commentRepository.findByIdAndSenderIdAndTaskId(id, senderId, taskId)
                .orElseThrow(() -> new NotFoundException(CommentEntity.class, id.toString()));
        commentRepository.delete(commentEntity);
    }
}
