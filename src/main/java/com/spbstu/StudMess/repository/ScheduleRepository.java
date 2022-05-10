package com.spbstu.StudMess.repository;

import com.spbstu.StudMess.model.LessonEntity;
import com.spbstu.StudMess.model.ScheduleEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    Page<ScheduleEntity> findAllByGroupId(@NonNull Long groupId, Pageable pageable);

    Page<ScheduleEntity> findAllBySubjectId(@NonNull Long subjectId, Pageable pageable);

    Page<ScheduleEntity> findAllByTeacherId(@NonNull Long teacherId, Pageable pageable);

    @Query("select s from ScheduleEntity s " +
            "join s.group g " +
            "join s.lesson l " +
            "where g.id = :groupId " +
            "and l.semester = :semester " +
            "and l.weekDay = :weekDay")
    List<ScheduleEntity> findAllByGroupIdAndSemesterAndWeekDay(@NonNull Long groupId, @NonNull Integer semester,
                                                               @NonNull Integer weekDay);

    @Query("select s from ScheduleEntity s " +
            "join s.group g " +
            "join s.lesson l " +
            "where g.id = :groupId " +
            "and l.semester = :semester " +
            "and l.weekDay = :weekDay " +
            "and l.lessonTime = :lessonTime")
    List<ScheduleEntity> findAllByGroupIdAndSemesterAndWeekDayAndLessonTime(@NonNull Long groupId,
                                                                            @NonNull Integer semester,
                                                                            @NonNull Integer weekDay,
                                                                            @NonNull Time lessonTime);

    @NonNull
    Boolean existsByLesson(@NonNull LessonEntity lesson);

    @NonNull
    @Query("select count(s) > 1 from ScheduleEntity s " +
            "where s.lesson.id = :lessonId")
    Boolean existsMoreThanOnceByLessonId(@NonNull Long lessonId);
}
