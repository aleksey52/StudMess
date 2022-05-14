package com.spbstu.StudMess.controller;

import com.spbstu.StudMess.controller.annotation.ApiV1;
import com.spbstu.StudMess.dto.response.MessageResponse;
import com.spbstu.StudMess.dto.response.OpenChatResponse;
import com.spbstu.StudMess.dto.response.UserResponse;
import com.spbstu.StudMess.model.ChatEntity;
import com.spbstu.StudMess.model.MessageEntity;
import com.spbstu.StudMess.model.UserEntity;
import com.spbstu.StudMess.service.ChatService;
import com.spbstu.StudMess.service.MessageService;
import com.spbstu.StudMess.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Chat")
@ApiV1
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;
    private final MessageService messageService;

    @GetMapping("/chats/{chatId}")
    @ResponseStatus(HttpStatus.OK)
    public OpenChatResponse find(@NonNull @PathVariable Long chatId) {
        return toOpenChatResponse(chatService.findById(chatId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/chats/{chatId}/messages")
    public Page<MessageResponse> findAllMessagesById(@NonNull @PathVariable Long chatId,
                                                     @ParameterObject Pageable pageable) {
        return messageService.findAllByChatId(chatId, pageable)
                .map(this::toResponse);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/chats/{chatId}/users")
    public Page<UserResponse> findAllUsersById(@NonNull @PathVariable Long chatId,
                                               @ParameterObject Pageable pageable) {
        return userService.findAllByChatId(chatId, pageable)
                .map(this::toResponse);
    }

    @PostMapping("/chats/{chatId}/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void addUserInChat(@NonNull @PathVariable Long chatId,
                              @NonNull @PathVariable Long userId) {
        chatService.addUserInChat(userId, chatId);
    }

    @DeleteMapping("/chats/{chatId}/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserFromChat(@NonNull @PathVariable Long chatId,
                                   @NonNull @PathVariable Long userId) {
        chatService.deleteUserFromChat(userId, chatId);
    }

    @NonNull
    private UserResponse toResponse(@NonNull UserEntity user) {
        return UserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .group(user.getGroup().getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
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
}
