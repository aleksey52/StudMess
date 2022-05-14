package com.spbstu.StudMess.controller;

import com.spbstu.StudMess.controller.annotation.ApiV1;
import com.spbstu.StudMess.dto.request.CommentRequest;
import com.spbstu.StudMess.dto.response.CommentResponse;
import com.spbstu.StudMess.model.CommentEntity;
import com.spbstu.StudMess.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "User comment")
@ApiV1
@RestController
@RequiredArgsConstructor
public class UserCommentController {

    private final CommentService commentService;

    @PostMapping("/users/{userId}/tasks/{taskId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse create(@NonNull @PathVariable Long userId,
                                  @NonNull @PathVariable Long taskId,
                                  @NonNull @Valid @RequestBody CommentRequest request) {
        return toResponse(commentService.create(userId, taskId, request.getContent(), request.getRecipientId()));
    }

    @GetMapping("/users/{userId}/tasks/{taskId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public Page<CommentResponse> findAll(@NonNull @PathVariable Long userId,
                                         @NonNull @PathVariable Long taskId,
                                         @ParameterObject Pageable pageable) {
        return commentService.findAllBySenderIdAndTaskId(userId, taskId, pageable)
                .map(this::toResponse);
    }

    @GetMapping("/users/{userId}/tasks/{taskId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponse find(@NonNull @PathVariable Long userId,
                                @NonNull @PathVariable Long taskId,
                                @NonNull @PathVariable Long commentId) {
        return toResponse(commentService.findByIdAndSenderIdAndTaskId(commentId, userId, taskId));
    }

    @DeleteMapping("/users/{userId}/tasks/{taskId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@NonNull @PathVariable Long userId,
                       @NonNull @PathVariable Long taskId,
                       @NonNull @PathVariable Long commentId) {
        commentService.delete(commentId, userId, taskId);
    }

    @PutMapping("/users/{userId}/tasks/{taskId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponse update(@NonNull @PathVariable Long userId,
                                  @NonNull @PathVariable Long taskId,
                                  @NonNull @PathVariable Long commentId,
                                  @NonNull @Valid @RequestBody CommentRequest request) {
        return toResponse(commentService.update(commentId, userId, taskId, request.getContent(),
                request.getRecipientId()));
    }

    @NonNull
    private CommentResponse toResponse(@NonNull CommentEntity comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .recipientId(comment.getRecipient().getId())
                .recipientLastAndFirstName(
                        comment.getRecipient().getLastName() + " " +
                                comment.getRecipient().getFirstName())
                .senderId(comment.getSender().getId())
                .senderLastAndFirstName(
                        comment.getSender().getLastName() + " " +
                                comment.getSender().getFirstName())
                .taskId(comment.getTask().getId())
                .creationDate(comment.getCreationDate())
                .updateDate(comment.getUpdateDate())
                .build();
    }
}
