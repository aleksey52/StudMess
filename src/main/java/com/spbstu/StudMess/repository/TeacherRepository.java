package com.spbstu.StudMess.repository;

import com.spbstu.StudMess.model.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
}
