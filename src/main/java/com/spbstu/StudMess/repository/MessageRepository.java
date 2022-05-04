package com.spbstu.StudMess.repository;

import com.spbstu.StudMess.model.ChatEntity;
import com.spbstu.StudMess.model.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
}
