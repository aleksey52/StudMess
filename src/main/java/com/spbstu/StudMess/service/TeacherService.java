package com.spbstu.StudMess.service;

import com.spbstu.StudMess.exception.NotFoundException;
import com.spbstu.StudMess.model.TeacherEntity;
import com.spbstu.StudMess.repository.TeacherRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    @NonNull
    @Transactional(readOnly = true)
    public TeacherEntity findById(@NonNull Long id) {
        return teacherRepository.findById(id).orElseThrow(() ->
                new NotFoundException(TeacherEntity.class, "id", id.toString()));
    }

    @NonNull
    @Transactional(readOnly = true)
    public Page<TeacherEntity> findAll(Pageable pageable) {
        return teacherRepository.findAll(pageable);
    }

    @NonNull
    @Transactional
    public TeacherEntity create(@NonNull String firstName, @NonNull String middleName, @NonNull String lastName,
                                @Nullable String email, @Nullable String phone) {
        final TeacherEntity teacherEntity = new TeacherEntity(firstName, middleName, lastName, email, phone);
        return teacherRepository.save(teacherEntity);
    }

    @NonNull
    @Transactional
    public TeacherEntity update(@NonNull Long id, @NonNull String firstName, @NonNull String middleName,
                                @NonNull String lastName, @Nullable String email, @Nullable String phone) {
        final TeacherEntity teacherEntity = teacherRepository.findById(id).orElseThrow(() ->
                new NotFoundException(TeacherEntity.class, id.toString()));

        teacherEntity.setFirstName(firstName);
        teacherEntity.setMiddleName(middleName);
        teacherEntity.setLastName(lastName);
        teacherEntity.setEmail(email);
        teacherEntity.setPhone(phone);

        return teacherRepository.save(teacherEntity);
    }

    @Transactional
    public void delete(@NonNull Long id) {
        final TeacherEntity teacherEntity = teacherRepository.findById(id).orElseThrow(() ->
                new NotFoundException(TeacherEntity.class, id.toString()));
        teacherRepository.delete(teacherEntity);
    }
}
