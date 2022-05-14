package com.spbstu.StudMess.service;

import com.spbstu.StudMess.dto.request.UserRequest;
import com.spbstu.StudMess.exception.NonUniqueValueException;
import com.spbstu.StudMess.exception.NotFoundException;
import com.spbstu.StudMess.model.GroupEntity;
import com.spbstu.StudMess.model.UserEntity;
import com.spbstu.StudMess.model.enums.Role;
import com.spbstu.StudMess.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final GroupService groupService;
    private final VerificationService verificationService;

    @NonNull
    @Transactional
    public UserEntity register(@NonNull String login, @NonNull String password,
                               @NonNull String firstName, @NonNull String middleName, @NonNull String lastName,
                               @NonNull String group, @NonNull String email, @Nullable String phone) {
        final GroupEntity groupEntity = groupService.findByName(group);
        final UserEntity userEntity = UserEntity.builder()
                .login(login)
                .role(Role.USER)
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .group(groupEntity)
                .phone(phone)
                .email(email)
                .password(passwordEncoder.encode(password))
                .verificationCode(UUID.randomUUID().toString())
                .build();

        UserEntity savedUser = userRepository.save(userEntity);

        verificationService.sendVerificationEmail(userEntity.getId());
        return savedUser;
    }

    @NonNull
    @Transactional
    public UserEntity create(@NonNull String login, @NonNull Role role, @NonNull Boolean enabled,
                             @NonNull String firstName, @NonNull String middleName, @NonNull String lastName,
                             @NonNull String group, @NonNull String email, @Nullable String phone) {
        String encodePassword = passwordEncoder.encode(UUID.randomUUID().toString());
        String verificationCode = Boolean.TRUE.equals((enabled)) ? null : UUID.randomUUID().toString();
        final GroupEntity groupEntity = groupService.findByName(group);
        final UserEntity userEntity = UserEntity.builder()
                .login(login)
                .role(role)
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .group(groupEntity)
                .phone(phone)
                .email(email)
                .password(encodePassword)
                .verificationCode(UUID.randomUUID().toString())
                .build();

        return userRepository.save(userEntity);
    }

    @NonNull
    @Transactional
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Transactional(readOnly = true)
    public Page<UserEntity> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<UserEntity> findAllByUsersId(@NonNull List<Long> usersId) {
        return userRepository.findByIdIn(usersId);
    }

    @NonNull
    @Transactional(readOnly = true)
    public Page<UserEntity> findAllByChatId(@NonNull Long chatId, Pageable pageable) {
        return userRepository.findAllByChatId(chatId, pageable);
    }

    @Transactional
    public UserEntity findByLogin(@NonNull String login) {
        return userRepository.findByLogin(login).orElseThrow(() ->
                new NotFoundException(UserEntity.class, "login", login));
    }

    @Transactional
    public UserEntity findByEmail(@NonNull String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException(UserEntity.class, "email", email));
    }

    @Transactional(readOnly = true)
    public UserEntity findById(@NonNull Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(UserEntity.class, id.toString()));
    }

    @Transactional(readOnly = true)
    public boolean isExists(@NonNull String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean isExistsWithLogin(@NonNull String login) {
        return userRepository.existsByLogin(login);
    }

    @Transactional
    public void delete(Long userId) {
        UserEntity user = findById(userId);
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Transactional
    public void enable(@NonNull Long userId, @NonNull Boolean enabled) {
        UserEntity user = findById(userId);
        user.setEnabled(enabled);
        userRepository.save(user);
    }

    @Transactional
    public UserEntity update(Long userId, UserRequest userRequest) {
        UserEntity user = findById(userId);

        acceptIfNotNullAndNotEmpty(userRequest.getFirstName(), user::setFirstName);
        acceptIfNotNullAndNotEmpty(userRequest.getMiddleName(), user::setMiddleName);
        acceptIfNotNullAndNotEmpty(userRequest.getLastName(), user::setLastName);

        final GroupEntity groupEntity = groupService.findByName(userRequest.getGroup());
        user.setGroup(groupEntity);

        acceptIfNotNullAndNotEmpty(userRequest.getLogin(), login -> {
            if (!userRepository.existsByLogin(login)) {
                user.setLogin(login);
            } else {
                throw new NonUniqueValueException(format("User with login=%s already exists", login));
            }
        });

        acceptIfNotNullAndNotEmpty(userRequest.getPhone(), phone -> {
            if (!userRepository.existsByPhone(phone)) {
                user.setPhone(phone);
            } else {
                throw new NonUniqueValueException(format("User with this phone=%s already exists", phone));
            }
        });

        userRepository.save(user);
        return user;
    }

    void acceptIfNotNullAndNotEmpty(String field, Consumer<String> consumer) {
        if (Objects.nonNull(field) && !field.isEmpty()) {
            consumer.accept(field);
        }
    }
}
