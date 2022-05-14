package com.spbstu.StudMess.controller;

import com.spbstu.StudMess.controller.annotation.ApiV1;
import com.spbstu.StudMess.dto.request.MessageRequest;
import com.spbstu.StudMess.dto.response.MessageResponse;
import com.spbstu.StudMess.model.MessageEntity;
import com.spbstu.StudMess.service.MessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "User message")
@ApiV1
@RestController
@RequiredArgsConstructor
public class UserMessageController {

    private final MessageService messageService;

    @PostMapping("/users/{userId}/chats/{chatId}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse create(@NonNull @PathVariable Long userId,
                                  @NonNull @PathVariable Long chatId,
                                  @NonNull @Valid @RequestBody MessageRequest request) {
        return toResponse(messageService.create(userId, chatId, request.getContent(), request.getRecipientId()));
    }

    @GetMapping("/users/{userId}/chats/{chatId}/messages")
    @ResponseStatus(HttpStatus.OK)
    public Page<MessageResponse> findAll(@NonNull @PathVariable Long userId,
                                         @NonNull @PathVariable Long chatId,
                                         @ParameterObject Pageable pageable) {
        return messageService.findAllBySenderIdAndChatId(userId, chatId, pageable)
                .map(this::toResponse);
    }

    @GetMapping("/users/{userId}/chats/{chatId}/messages/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse find(@NonNull @PathVariable Long userId,
                                @NonNull @PathVariable Long chatId,
                                @NonNull @PathVariable Long messageId) {
        return toResponse(messageService.findByIdAndSenderIdAndChatId(messageId, userId, chatId));
    }

    @DeleteMapping("/users/{userId}/chats/{chatId}/messages/{messageId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@NonNull @PathVariable Long userId,
                       @NonNull @PathVariable Long chatId,
                       @NonNull @PathVariable Long messageId) {
        messageService.delete(messageId, userId, chatId);
    }

    @PutMapping("/users/{userId}/chats/{chatId}/messages/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse update(@NonNull @PathVariable Long userId,
                                  @NonNull @PathVariable Long chatId,
                                  @NonNull @PathVariable Long messageId,
                                  @NonNull @Valid @RequestBody MessageRequest request) {
        return toResponse(messageService.update(messageId, userId, chatId, request.getContent(),
                request.getRecipientId()));
    }

    @NonNull
    private MessageResponse toResponse(@NonNull MessageEntity message) {
        return MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .recipientId(message.getRecipient().getId())
                .recipientLastAndFirstName(
                        message.getRecipient().getLastName() + " " +
                                message.getRecipient().getFirstName())
                .senderId(message.getSender().getId())
                .senderLastAndFirstName(
                        message.getSender().getLastName() + " " +
                                message.getSender().getFirstName())
                .chatId(message.getChat().getId())
                .creationDate(message.getCreationDate())
                .updateDate(message.getUpdateDate())
                .build();
    }
}
