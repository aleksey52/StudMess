package com.spbstu.StudMess.repository;

import com.spbstu.StudMess.model.ChatEntity;
import com.spbstu.StudMess.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
