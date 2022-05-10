package com.spbstu.StudMess.repository;

import com.spbstu.StudMess.model.LessonEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {

    Optional<LessonEntity> findBySemesterAndWeekDayAndLessonTime(@NonNull Integer semester, @NonNull Integer weekDay,
                                                     @NonNull Time lessonTime);

    @NonNull
    Boolean existsBySemesterAndWeekDayAndLessonTime(@NonNull Integer semester, @NonNull Integer weekDay,
                                                    @NonNull Time lessonTime);
}
