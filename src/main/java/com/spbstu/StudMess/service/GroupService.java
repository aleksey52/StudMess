package com.spbstu.StudMess.service;

import com.spbstu.StudMess.exception.NotFoundException;
import com.spbstu.StudMess.model.GroupEntity;
import com.spbstu.StudMess.model.UserEntity;
import com.spbstu.StudMess.repository.GroupRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    @Transactional
    public GroupEntity findByName(@NonNull String name) {
        return groupRepository.findByName(name).orElseThrow(() ->
                new NotFoundException(GroupEntity.class, "name", name));
    }
}
