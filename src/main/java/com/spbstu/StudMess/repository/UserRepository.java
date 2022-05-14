package com.spbstu.StudMess.repository;

import com.spbstu.StudMess.model.UserEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByLogin(@NonNull String login);

    Optional<UserEntity> findByEmail(@NonNull String email);

    List<UserEntity> findByIdIn(@NonNull Collection<Long> usersId);

    @Query("select u from UserEntity u " +
            "join u.chats c " +
            "where c.id = :chatId")
    Page<UserEntity> findAllByChatId(@NonNull Long chatId, Pageable pageable);

    @NonNull
    Boolean existsByLogin(@NonNull String login);

    @NonNull
    Boolean existsByPhone(@NonNull String phone);

    @NonNull
    Boolean existsByEmail(@NonNull String email);
}
