package com.spbstu.StudMess.repository;

import com.spbstu.StudMess.model.GroupEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    Optional<GroupEntity> findByName(@NonNull String name);

    Page<GroupEntity> findGroupEntitiesByNameContains(@NonNull String substring, Pageable pageable);

    @NonNull
    Boolean existsByName(@NonNull String name);
}
