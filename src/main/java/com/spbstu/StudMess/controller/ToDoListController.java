package com.spbstu.StudMess.controller;

import com.spbstu.StudMess.controller.annotation.ApiV1;
import com.spbstu.StudMess.dto.request.ToDoTaskRequest;
import com.spbstu.StudMess.dto.response.ToDoTaskResponse;
import com.spbstu.StudMess.model.UserTaskEntity;
import com.spbstu.StudMess.service.UserTaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "ToDo list")
@ApiV1
@RestController
@RequiredArgsConstructor
public class ToDoListController {

    private final UserTaskService userTaskService;

    @PostMapping("/users/{userId}/tasks/{taskId}/todo")
    @ResponseStatus(HttpStatus.CREATED)
    public ToDoTaskResponse create(@NonNull @PathVariable Long userId,
                                   @NonNull @PathVariable Long taskId) {
        return toResponse(userTaskService.create(userId, taskId));
    }

    @GetMapping("/users/{userId}/todo")
    @ResponseStatus(HttpStatus.OK)
    public Page<ToDoTaskResponse> findAll(@NonNull @PathVariable Long userId,
                                          @ParameterObject Pageable pageable) {
        return userTaskService.findAllByUserId(userId, pageable)
                .map(this::toResponse);
    }

    @GetMapping("/users/{userId}/tasks/{taskId}/todo")
    @ResponseStatus(HttpStatus.OK)
    public ToDoTaskResponse find(@NonNull @PathVariable Long userId,
                                 @NonNull @PathVariable Long taskId) {
        return toResponse(userTaskService.findByUserIdAndTaskId(userId, taskId));
    }

    @DeleteMapping("/users/{userId}/tasks/{taskId}/todo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@NonNull @PathVariable Long userId,
                       @NonNull @PathVariable Long taskId) {
        userTaskService.delete(userId, taskId);
    }

    @PutMapping("/users/{userId}/tasks/{taskId}/todo")
    @ResponseStatus(HttpStatus.OK)
    public ToDoTaskResponse update(@NonNull @PathVariable Long userId,
                                   @NonNull @PathVariable Long taskId,
                                   @NonNull @Valid @RequestBody ToDoTaskRequest request) {
        return toResponse(userTaskService.update(userId, taskId, request.getDone(), request.getScore()));
    }

    @NonNull
    public ToDoTaskResponse toResponse(@NonNull UserTaskEntity userTask) {
        return new ToDoTaskResponse(userTask.getId(),
                userTask.getUser().getId(),
                userTask.getTask().getId(),
                userTask.getDone(),
                userTask.getScore());
    }
}
