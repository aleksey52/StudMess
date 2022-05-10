package com.spbstu.StudMess.service;

import com.spbstu.StudMess.exception.NotFoundException;
import com.spbstu.StudMess.model.*;
import com.spbstu.StudMess.repository.LessonRepository;
import com.spbstu.StudMess.repository.ScheduleRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final LessonRepository lessonRepository;
    private final GroupService groupService;
    private final SubjectService subjectService;
    private final TeacherService teacherService;

    @NonNull
    @Transactional(readOnly = true)
    public ScheduleEntity findById(@NonNull Long id) {
        return scheduleRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ScheduleEntity.class, id.toString()));
    }

    @NonNull
    @Transactional(readOnly = true)
    public Page<ScheduleEntity> findAllByGroupId(@NonNull Long groupId, Pageable pageable) {
        return scheduleRepository.findAllByGroupId(groupId, pageable);
    }

    @NonNull
    @Transactional(readOnly = true)
    public Page<ScheduleEntity> findAllBySubjectId(@NonNull Long subjectId, Pageable pageable) {
        return scheduleRepository.findAllBySubjectId(subjectId, pageable);
    }

    @NonNull
    @Transactional(readOnly = true)
    public Page<ScheduleEntity> findAllByTeacherId(@NonNull Long teacherId, Pageable pageable) {
        return scheduleRepository.findAllByTeacherId(teacherId, pageable);
    }

    @NonNull
    @Transactional(readOnly = true)
    public List<ScheduleEntity> findAllByGroupIdAndSemesterAndWeekDay(@NonNull Long groupId, @NonNull Integer semester,
                                                                      @NonNull Integer weekDay) {
        return scheduleRepository.findAllByGroupIdAndSemesterAndWeekDay(groupId, semester, weekDay);
    }

    @NonNull
    @Transactional(readOnly = true)
    public List<ScheduleEntity> findAllByGroupIdAndSemesterAndWeekDayAndLessonTime(@NonNull Long groupId,
                                                                                   @NonNull Integer semester,
                                                                                   @NonNull Integer weekDay,
                                                                                   @NonNull Time lessonTime) {
        return scheduleRepository.findAllByGroupIdAndSemesterAndWeekDayAndLessonTime(groupId, semester, weekDay,
                lessonTime);
    }

    @NonNull
    @Transactional
    public ScheduleEntity create(@NonNull Integer semester, @NonNull Integer weekDay, @NonNull Time lessonTime,
                                 @NonNull Long groupId, @NonNull Long subjectId, @NonNull Long teacherId) {
        LessonEntity lesson;
        if (!lessonRepository.existsBySemesterAndWeekDayAndLessonTime(semester, weekDay, lessonTime)) {
            lesson = new LessonEntity(semester, weekDay, lessonTime);
            lessonRepository.save(lesson);
        } else {
            lesson = lessonRepository.findBySemesterAndWeekDayAndLessonTime(semester, weekDay, lessonTime).orElseThrow(() ->
                    new NotFoundException(LessonEntity.class, "semester", "weekDay", "lessonTime",
                            semester.toString(), weekDay.toString(), lessonTime.toString()));
        }

        final GroupEntity group = groupService.findById(groupId);
        final SubjectEntity subject = subjectService.findById(subjectId);
        final TeacherEntity teacher = teacherService.findById(teacherId);
        final ScheduleEntity schedule = new ScheduleEntity(group, subject, teacher, lesson);

        return scheduleRepository.save(schedule);
    }

    @NonNull
    @Transactional
    public ScheduleEntity update(@NonNull Long scheduleId, @NonNull Integer semester, @NonNull Integer weekDay,
                                 @NonNull Time lessonTime, @NonNull Long groupId, @NonNull Long subjectId,
                                 @NonNull Long teacherId) {
        final ScheduleEntity schedule = scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new NotFoundException(ScheduleEntity.class, scheduleId.toString()));
        LessonEntity lesson = schedule.getLesson();

        if (scheduleRepository.existsMoreThanOnceByLessonId(lesson.getId())) {
            lesson = new LessonEntity(semester, weekDay, lessonTime);
            lessonRepository.save(lesson);
        } else {
            lesson.setSemester(semester);
            lesson.setWeekDay(weekDay);
            lesson.setLessonTime(lessonTime);
            lessonRepository.save(lesson);
        }

        final GroupEntity group = groupService.findById(groupId);
        final SubjectEntity subject = subjectService.findById(subjectId);
        final TeacherEntity teacher = teacherService.findById(teacherId);

        schedule.setGroup(group);
        schedule.setSubject(subject);
        schedule.setTeacher(teacher);
        schedule.setLesson(lesson);

        return scheduleRepository.save(schedule);
    }

    @Transactional
    public void delete(@NonNull Long id) {
        final ScheduleEntity scheduleEntity = scheduleRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ScheduleEntity.class, id.toString()));
        final LessonEntity lesson = scheduleEntity.getLesson();
        scheduleRepository.delete(scheduleEntity);

        if (!scheduleRepository.existsByLesson(lesson)) {
            lessonRepository.delete(lesson);
        }
    }
}
