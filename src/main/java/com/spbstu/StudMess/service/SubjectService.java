package com.spbstu.StudMess.service;

import com.spbstu.StudMess.exception.NonUniqueValueException;
import com.spbstu.StudMess.exception.NotFoundException;
import com.spbstu.StudMess.model.SubjectEntity;
import com.spbstu.StudMess.repository.SubjectRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @NonNull
    @Transactional(readOnly = true)
    public SubjectEntity findById(@NonNull Long id) {
        return subjectRepository.findById(id).orElseThrow(() ->
                new NotFoundException(SubjectEntity.class, "id", id.toString()));
    }

    @NonNull
    @Transactional(readOnly = true)
    public SubjectEntity findByName(@NonNull String name) {
        return subjectRepository.findByName(name).orElseThrow(() ->
                new NotFoundException(SubjectEntity.class, "name", name));
    }

    @NonNull
    @Transactional(readOnly = true)
    public Page<SubjectEntity> findAll(String subjectNameContains, Pageable pageable) {
        if (Objects.nonNull(subjectNameContains) && !subjectNameContains.isEmpty()) {
            return subjectRepository.findSubjectEntitiesByNameContains(subjectNameContains, pageable);
        }
        return subjectRepository.findAll(pageable);
    }

    @NonNull
    @Transactional
    public SubjectEntity create(@NonNull String name) {
        if (subjectRepository.existsByName(name)) {
            return subjectRepository.findByName(name).orElseThrow(() ->
                    new NotFoundException(SubjectEntity.class, "name", name));
        }

        final SubjectEntity subjectEntity = new SubjectEntity(name);
        return subjectRepository.save(subjectEntity);
    }

    @NonNull
    @Transactional
    public SubjectEntity update(@NonNull Long id, @NonNull String name) {
        if (subjectRepository.existsByName(name)) {
            throw new NonUniqueValueException(format("Subject with name=%s already exists", name));
        }

        final SubjectEntity subjectEntity = subjectRepository.findById(id).orElseThrow(() ->
                new NotFoundException(SubjectEntity.class, id.toString()));
        subjectEntity.setName(name);
        return subjectRepository.save(subjectEntity);
    }

    @Transactional
    public void delete(@NonNull Long id) {
        final SubjectEntity subjectEntity = subjectRepository.findById(id).orElseThrow(() ->
                new NotFoundException(SubjectEntity.class, id.toString()));
        subjectRepository.delete(subjectEntity);
    }

    @Transactional
    public void delete(@NonNull String name) {
        final SubjectEntity subjectEntity = subjectRepository.findByName(name).orElseThrow(() ->
                new NotFoundException(SubjectEntity.class, name));
        subjectRepository.delete(subjectEntity);
    }
}
