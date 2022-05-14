package com.spbstu.StudMess.controller;

import com.spbstu.StudMess.controller.annotation.ApiV1;
import com.spbstu.StudMess.dto.request.ChatRequest;
import com.spbstu.StudMess.dto.response.CloseChatResponse;
import com.spbstu.StudMess.dto.response.MessageResponse;
import com.spbstu.StudMess.dto.response.OpenChatResponse;
import com.spbstu.StudMess.model.ChatEntity;
import com.spbstu.StudMess.model.MessageEntity;
import com.spbstu.StudMess.service.ChatService;
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

@Tag(name = "User chat")
@ApiV1
@RestController
@RequiredArgsConstructor
public class UserChatController {

    private final ChatService chatService;
    private final MessageService messageService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{userId}/chats")
    public Page<CloseChatResponse> findAll(@NonNull @PathVariable Long userId,
                                           @RequestParam(name = "nameContains", required = false) String nameContains,
                                           @ParameterObject Pageable pageable) {
        return chatService.findAllByIncomingUser(userId, nameContains, pageable)
                .map(this::toCloseChatResponse);
    }

    @PostMapping("/users/{userId}/chats/{interlocutorId}")
    @ResponseStatus(HttpStatus.CREATED)
    public OpenChatResponse createPersonalChat(@NonNull @PathVariable Long userId,
                                               @NonNull @PathVariable Long interlocutorId) {
        return toOpenChatResponse(chatService.createPersonalChat(userId, interlocutorId));
    }

    @PostMapping("/users/{userId}/chats")
    @ResponseStatus(HttpStatus.CREATED)
    public OpenChatResponse createGroupChat(@NonNull @PathVariable Long userId,
                                            @NonNull @Valid @RequestBody ChatRequest request) {
        return toOpenChatResponse(chatService.createGroupChat(request.getName(), userId, request.getUsersId()));
    }

    @DeleteMapping("/users/{userId}/chats/{chatId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@NonNull @PathVariable Long userId,
                       @NonNull @PathVariable Long chatId) {
        chatService.delete(userId, chatId);
    }

    @PutMapping("/users/{userId}/chats/{chatId}")
    @ResponseStatus(HttpStatus.OK)
    public OpenChatResponse update(@NonNull @PathVariable Long userId,
                                   @NonNull @PathVariable Long chatId,
                                   @NonNull @Valid @RequestBody ChatRequest request) {
        return toOpenChatResponse(chatService.update(userId, chatId, request.getName(), request.getUsersId()));
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

    @NonNull
    private OpenChatResponse toOpenChatResponse(@NonNull ChatEntity chat) {
        return new OpenChatResponse(
                chat.getId(),
                chat.getName(),
                chat.getInitiator().getId(),
                chat.getCreationDate());
    }

    @NonNull
    private CloseChatResponse toCloseChatResponse(@NonNull ChatEntity chat) {
        final MessageEntity messageEntity = messageService.findLastByChatId(chat.getId());
        MessageResponse messageResponse = null;
        if (messageEntity != null) {
            messageResponse = toResponse(messageEntity);
        }

        return new CloseChatResponse(
                chat.getId(),
                chat.getName(),
                chat.getInitiator().getId(),
                chat.getCreationDate(),
                messageResponse);
    }
}
