package com.spbstu.StudMess.controller;

import com.spbstu.StudMess.controller.annotation.ApiV1;
import com.spbstu.StudMess.dto.request.TaskRequest;
import com.spbstu.StudMess.dto.response.TaskResponse;
import com.spbstu.StudMess.model.TaskEntity;
import com.spbstu.StudMess.service.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Task")
@ApiV1
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/schedule/{scheduleId}/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@NonNull @PathVariable Long scheduleId,
                               @NonNull @Valid @RequestBody TaskRequest request) {
        return toResponse(taskService.create(scheduleId, request.getName(), request.getContext(), request.getDeadline()));
    }

    @GetMapping("/tasks")
    @ResponseStatus(HttpStatus.OK)
    public Page<TaskResponse> findAll(@ParameterObject Pageable pageable) {
        return taskService.findAll(pageable)
                .map(this::toResponse);
    }

    @GetMapping("/tasks/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponse find(@NonNull @PathVariable Long taskId) {
        return toResponse(taskService.findById(taskId));
    }

    @DeleteMapping("/tasks/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@NonNull @PathVariable Long taskId) {
        taskService.delete(taskId);
    }

    @PutMapping("/schedule/{scheduleId}/tasks/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponse update(@NonNull @PathVariable Long scheduleId,
                               @NonNull @PathVariable Long taskId,
                               @NonNull @Valid @RequestBody TaskRequest request) {
        return toResponse(taskService.update(taskId, scheduleId, request.getName(), request.getContext(),
                request.getDeadline()));
    }

    @NonNull
    public TaskResponse toResponse(@NonNull TaskEntity taskEntity) {
        return new TaskResponse(taskEntity.getId(),
                taskEntity.getSchedule().getId(),
                taskEntity.getName(),
                taskEntity.getContext(),
                taskEntity.getDeadline());
    }
}
