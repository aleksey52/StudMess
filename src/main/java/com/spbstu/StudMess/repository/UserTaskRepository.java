package com.spbstu.StudMess.repository;

import com.spbstu.StudMess.model.UserTaskEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTaskRepository extends JpaRepository<UserTaskEntity, Long> {

    Page<UserTaskEntity> findAllByUserId(@NonNull Long userId, Pageable pageable);

    Optional<UserTaskEntity> findByUserIdAndTaskId(@NonNull Long userId, @NonNull Long taskId);
}
