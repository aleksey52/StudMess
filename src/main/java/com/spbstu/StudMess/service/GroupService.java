package com.spbstu.StudMess.service;

import com.spbstu.StudMess.exception.NonUniqueValueException;
import com.spbstu.StudMess.exception.NotFoundException;
import com.spbstu.StudMess.model.GroupEntity;
import com.spbstu.StudMess.repository.GroupRepository;
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
public class GroupService {

    private final GroupRepository groupRepository;

    @NonNull
    @Transactional(readOnly = true)
    public GroupEntity findById(@NonNull Long id) {
        return groupRepository.findById(id).orElseThrow(() ->
                new NotFoundException(GroupEntity.class, "id", id.toString()));
    }

    @NonNull
    @Transactional(readOnly = true)
    public GroupEntity findByName(@NonNull String name) {
        return groupRepository.findByName(name).orElseThrow(() ->
                new NotFoundException(GroupEntity.class, "name", name));
    }

    @NonNull
    @Transactional(readOnly = true)
    public Page<GroupEntity> findAll(String groupNameContains, Pageable pageable) {
        if (Objects.nonNull(groupNameContains) && !groupNameContains.isEmpty()) {
            return groupRepository.findGroupEntitiesByNameContains(groupNameContains, pageable);
        }
        return groupRepository.findAll(pageable);
    }

    @NonNull
    @Transactional
    public GroupEntity create(@NonNull String name) {
        if (groupRepository.existsByName(name)) {
            return groupRepository.findByName(name).orElseThrow(() ->
                    new NotFoundException(GroupEntity.class, "name", name));
        }

        final GroupEntity groupEntity = new GroupEntity(name);
        return groupRepository.save(groupEntity);
    }

    @NonNull
    @Transactional
    public GroupEntity update(@NonNull Long id, @NonNull String name) {
        if (groupRepository.existsByName(name)) {
            throw new NonUniqueValueException(format("Group with name=%s already exists", name));
        }

        final GroupEntity groupEntity = groupRepository.findById(id).orElseThrow(() ->
                new NotFoundException(GroupEntity.class, id.toString()));
        groupEntity.setName(name);
        return groupRepository.save(groupEntity);
    }

    @Transactional
    public void delete(@NonNull Long id) {
        final GroupEntity groupEntity = groupRepository.findById(id).orElseThrow(() ->
                new NotFoundException(GroupEntity.class, id.toString()));
        groupRepository.delete(groupEntity);
    }

    @Transactional
    public void delete(@NonNull String name) {
        final GroupEntity groupEntity = groupRepository.findByName(name).orElseThrow(() ->
                new NotFoundException(GroupEntity.class, name));
        groupRepository.delete(groupEntity);
    }
}
