package com.spbstu.StudMess.controller;

import com.spbstu.StudMess.controller.annotation.ApiV1;
import com.spbstu.StudMess.dto.request.TeacherRequest;
import com.spbstu.StudMess.dto.response.TeacherResponse;
import com.spbstu.StudMess.model.TeacherEntity;
import com.spbstu.StudMess.service.TeacherService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Teacher")
@ApiV1
@RestController
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/teachers")
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherResponse create(@NonNull @Valid @RequestBody TeacherRequest request) {
        return toResponse(teacherService.create(request.getFirstName(), request.getMiddleName(), request.getLastName(),
                request.getEmail(), request.getPhone()));
    }

    @GetMapping("/teachers")
    @ResponseStatus(HttpStatus.OK)
    public Page<TeacherResponse> findAll(@ParameterObject Pageable pageable) {
        return teacherService.findAll(pageable)
                .map(this::toResponse);
    }

    @GetMapping("/teachers/{teacherId}")
    @ResponseStatus(HttpStatus.OK)
    public TeacherResponse find(@NonNull @PathVariable Long teacherId) {
        return toResponse(teacherService.findById(teacherId));
    }

    @DeleteMapping("/teachers/{teacherId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@NonNull @PathVariable Long teacherId) {
        teacherService.delete(teacherId);
    }

    @PutMapping("/teachers/{teacherId}")
    @ResponseStatus(HttpStatus.OK)
    public TeacherResponse update(@NonNull @PathVariable Long teacherId,
                                  @NonNull @Valid @RequestBody TeacherRequest request) {
        return toResponse(teacherService.update(teacherId, request.getFirstName(), request.getMiddleName(),
                request.getLastName(), request.getEmail(), request.getPhone()));
    }

    @NonNull
    public TeacherResponse toResponse(@NonNull TeacherEntity teacherEntity) {
        return new TeacherResponse(teacherEntity.getId(), teacherEntity.getFirstName(), teacherEntity.getMiddleName(),
                teacherEntity.getLastName(), teacherEntity.getEmail(), teacherEntity.getPhone());
    }
}
