package com.spbstu.StudMess.repository;

import com.spbstu.StudMess.model.ChatEntity;
import com.spbstu.StudMess.model.CommentEntity;
import com.spbstu.StudMess.model.MessageEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Page<CommentEntity> findAllByTaskId(@NonNull Long taskId, Pageable pageable);

    Page<CommentEntity> findAllBySenderId(@NonNull Long senderId, Pageable pageable);

    Page<CommentEntity> findAllBySenderIdAndTaskId(@NonNull Long senderId, @NonNull Long taskId, Pageable pageable);

    Optional<CommentEntity> findByIdAndSenderIdAndTaskId(@NonNull Long commentId, @NonNull Long senderId,
                                                         @NonNull Long taskId);
}
