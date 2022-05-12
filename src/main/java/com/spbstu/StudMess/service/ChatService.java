package com.spbstu.StudMess.service;

import com.spbstu.StudMess.exception.NotFoundException;
import com.spbstu.StudMess.model.ChatEntity;
import com.spbstu.StudMess.model.UserEntity;
import com.spbstu.StudMess.repository.ChatRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserService userService;

    @NonNull
    @Transactional(readOnly = true)
    public ChatEntity findById(@NonNull Long id) {
        return chatRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ChatEntity.class, id.toString()));
    }

    @NonNull
    @Transactional(readOnly = true)
    public ChatEntity findByName(@NonNull String name) {
        return chatRepository.findByName(name).orElseThrow(() ->
                new NotFoundException(ChatEntity.class, name));
    }

    @NonNull
    @Transactional(readOnly = true)
    public Page<ChatEntity> findAllByIncomingUser(@NonNull Long userId, Pageable pageable) {
        return chatRepository.findAllByIncomingUser(userId, pageable); // сортировка по последнему сообщению, а потом по дате создания чата
    }

    @NonNull
    @Transactional(readOnly = true)
    public Page<ChatEntity> findAllByIncomingUser(@NonNull Long userId, @Nullable String nameContains, Pageable pageable) {
        Page<ChatEntity> chatEntities;
        if (Objects.isNull(nameContains)) {
            chatEntities = chatRepository.findAllByIncomingUser(userId, pageable);
        } else {
            chatEntities = chatRepository.findAllByIncomingUserAndNameContains(userId, nameContains, pageable);
        }
        return chatEntities;
    }

    @NonNull
    @Transactional
    public ChatEntity createPersonalChat(@NonNull Long initiatorId, @NonNull Long interlocutorId) {

        final UserEntity initiator = userService.findById(initiatorId);
        final UserEntity interlocutor = userService.findById(interlocutorId);

        final String name = initiator.getLastName() + " " + initiator.getFirstName() + ", " +
                interlocutor.getLastName() + " " + interlocutor.getFirstName();

        final ChatEntity chatEntity = new ChatEntity(name, initiator);
        chatEntity.setUsers(List.of(initiator, interlocutor));

        return chatRepository.save(chatEntity);
    }

    @NonNull
    @Transactional
    public ChatEntity createGroupChat(@NonNull String name, @NonNull Long initiatorId, @Nullable List<Long> usersId) {

        final UserEntity initiator = userService.findById(initiatorId);
        final ChatEntity chatEntity = new ChatEntity(name, initiator);

        if (usersId != null) {
            List<UserEntity> users = userService.findAllByUsersId(usersId);
            chatEntity.setUsers(users);
        } else {
            chatEntity.setUsers(List.of(initiator));
        }

        return chatRepository.save(chatEntity);
    }

    @NonNull
    @Transactional
    public ChatEntity update(@NonNull Long chatId, @NonNull String name, @Nullable List<Long> usersId) {

        final ChatEntity chatEntity = chatRepository.findById(chatId).orElseThrow(() ->
                new NotFoundException(ChatEntity.class, chatId.toString()));

        chatEntity.setName(name);

        if (usersId != null) {
            List<UserEntity> existUsers = userService.findAllByUsersId(usersId);//userService.findByNameIn(usersId);
            List<Long> existUsersId = existUsers.stream()
                    .map(UserEntity::getId)
                    .collect(Collectors.toList());

            List<UserEntity> newUsers = usersId.stream()
                    .filter(userId -> !existUsersId.contains(userId))
                    .map(userService::findById)
                    .collect(Collectors.toList());

            ArrayList<UserEntity> users = new ArrayList<>(existUsers);
            users.addAll(newUsers);
            chatEntity.setUsers(users);
        }

        return chatRepository.save(chatEntity);
    }

    @Transactional
    public void delete(@NonNull Long id) {
        final ChatEntity chatEntity = chatRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ChatEntity.class, id.toString()));
        chatRepository.delete(chatEntity);
    }

    @Transactional
    public void addUserInChat(@NonNull Long chatId, @NonNull Long userId) {
        final ChatEntity chatEntity = chatRepository.findById(chatId).orElseThrow(() ->
                new NotFoundException(ChatEntity.class, chatId.toString()));
        final UserEntity userEntity = userService.findById(userId);

        final List<UserEntity> users = chatEntity.getUsers();
        users.add(userEntity);
        chatEntity.setUsers(users);

        chatRepository.save(chatEntity);
    }

    @Transactional
    public void deleteUserFromChat(@NonNull Long chatId, @NonNull Long userId) {
        final ChatEntity chatEntity = chatRepository.findById(chatId).orElseThrow(() ->
                new NotFoundException(ChatEntity.class, chatId.toString()));
        final UserEntity userEntity = userService.findById(userId);

        final List<UserEntity> users = chatEntity.getUsers();
        users.remove(userEntity);
        chatEntity.setUsers(users);

        chatRepository.save(chatEntity);
    }
}
