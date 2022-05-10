package com.spbstu.StudMess.repository;

import com.spbstu.StudMess.model.ChatEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

    Optional<ChatEntity> findByName(@NonNull String name);

    /* TODO
    add sorting by date last message and date created chat
     */
    @Query("select c from ChatEntity c " +
            "join c.users u " +
            "join c.messages m " +
            "where u.id = :userId")
    Page<ChatEntity> findAllByIncomingUser(@NonNull Long userId, Pageable pageable);

    @Query("select c from ChatEntity c " +
            "join c.users u " +
            "join c.messages m " +
            "where u.id = :userId " +
            "and lower(c.name) like lower(concat('%', :nameContains, '%'))")
    Page<ChatEntity> findAllByIncomingUserAndNameContains(@NonNull Long userId,
                                                          @NonNull @Param("nameContains") String nameContains,
                                                          Pageable pageable);
}
