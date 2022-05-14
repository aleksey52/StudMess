package com.spbstu.StudMess.controller;

import com.spbstu.StudMess.controller.annotation.ApiV1;
import com.spbstu.StudMess.dto.request.ScheduleRequest;
import com.spbstu.StudMess.dto.response.GroupResponse;
import com.spbstu.StudMess.dto.response.ScheduleResponse;
import com.spbstu.StudMess.dto.response.SubjectResponse;
import com.spbstu.StudMess.dto.response.TeacherResponse;
import com.spbstu.StudMess.model.ScheduleEntity;
import com.spbstu.StudMess.service.ScheduleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Schedule")
@ApiV1
@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/schedule/{scheduleId}")
    @ResponseStatus(HttpStatus.OK)
    public ScheduleResponse findById(@NonNull @PathVariable Long scheduleId) {
        return toResponse(scheduleService.findById(scheduleId));
    }

    @GetMapping("/schedule/groups/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    public Page<ScheduleResponse> findAllByGroupId(@NonNull @PathVariable Long groupId,
                                                   @ParameterObject Pageable pageable) {
        return scheduleService.findAllByGroupId(groupId, pageable)
                .map(this::toResponse);
    }

    @GetMapping("/schedule/subjects/{subjectId}")
    @ResponseStatus(HttpStatus.OK)
    public Page<ScheduleResponse> findAllBySubjectId(@NonNull @PathVariable Long subjectId,
                                                     @ParameterObject Pageable pageable) {
        return scheduleService.findAllBySubjectId(subjectId, pageable)
                .map(this::toResponse);
    }

    @GetMapping("/schedule/teachers/{teacherId}")
    @ResponseStatus(HttpStatus.OK)
    public Page<ScheduleResponse> findAllByTeacherId(@NonNull @PathVariable Long teacherId,
                                                     @ParameterObject Pageable pageable) {
        return scheduleService.findAllByTeacherId(teacherId, pageable)
                .map(this::toResponse);
    }

    @GetMapping("/schedule/groups/{groupId}/{semester}/{weekDay}")
    @ResponseStatus(HttpStatus.OK)
    public List<ScheduleResponse> findAllByGroupIdAndSemesterAndWeekDay(@NonNull @PathVariable Long groupId,
                                                                        @NonNull @PathVariable Integer semester,
                                                                        @NonNull @PathVariable Integer weekDay) {
        return scheduleService.findAllByGroupIdAndSemesterAndWeekDay(groupId, semester, weekDay)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @PostMapping("/schedule")
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleResponse create(@NonNull @Valid @RequestBody ScheduleRequest request) {
        return toResponse(scheduleService.create(
                request.getSemester(),
                request.getWeekDay(),
                request.getLessonTime(),
                request.getGroupId(),
                request.getSubjectId(),
                request.getTeacherId()
        ));
    }

    @DeleteMapping("/schedule/{scheduleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@NonNull @PathVariable Long scheduleId) {
        scheduleService.delete(scheduleId);
    }

    @PutMapping("/subjects/{scheduleId}")
    @ResponseStatus(HttpStatus.OK)
    public ScheduleResponse update(@NonNull @PathVariable Long scheduleId,
                                   @NonNull @Valid @RequestBody ScheduleRequest request) {
        return toResponse(scheduleService.update(
                scheduleId,
                request.getSemester(),
                request.getWeekDay(),
                request.getLessonTime(),
                request.getGroupId(),
                request.getSubjectId(),
                request.getTeacherId()
        ));
    }

    @NonNull
    private ScheduleResponse toResponse(@NonNull ScheduleEntity schedule) {
        final GroupResponse groupResponse = new GroupResponse(schedule.getGroup().getId(), schedule.getGroup().getName());
        final SubjectResponse subjectResponse = new SubjectResponse(schedule.getSubject().getId(), schedule.getSubject().getName());
        final TeacherResponse teacherResponse = new TeacherResponse(
                schedule.getTeacher().getId(),
                schedule.getTeacher().getFirstName(),
                schedule.getTeacher().getMiddleName(),
                schedule.getTeacher().getLastName(),
                schedule.getTeacher().getEmail(),
                schedule.getTeacher().getPhone()
        );

        return ScheduleResponse.builder()
                .id(schedule.getId())
                .lessonId(schedule.getLesson().getId())
                .semester(schedule.getLesson().getSemester())
                .weekDay(schedule.getLesson().getWeekDay())
                .lessonTime(schedule.getLesson().getLessonTime())
                .group(groupResponse)
                .subject(subjectResponse)
                .teacher(teacherResponse)
                .build();
    }
}
