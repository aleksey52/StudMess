package com.spbstu.StudMess.repository;

import com.spbstu.StudMess.model.SubjectEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {

    Optional<SubjectEntity> findByName(@NonNull String name);

    Page<SubjectEntity> findSubjectEntitiesByNameContains(@NonNull String substring, Pageable pageable);

    @NonNull
    Boolean existsByName(@NonNull String name);
}
