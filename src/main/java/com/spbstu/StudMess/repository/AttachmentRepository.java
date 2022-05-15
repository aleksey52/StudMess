package com.spbstu.StudMess.repository;

import com.spbstu.StudMess.model.AttachmentEntity;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Long> {

    List<AttachmentEntity> findAllByMessageId(@NonNull Long messageId, Pageable pageable);
}
