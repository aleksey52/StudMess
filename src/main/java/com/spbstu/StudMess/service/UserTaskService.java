package com.spbstu.StudMess.service;

import com.spbstu.StudMess.exception.NotFoundException;
import com.spbstu.StudMess.model.TaskEntity;
import com.spbstu.StudMess.model.UserEntity;
import com.spbstu.StudMess.model.UserTaskEntity;
import com.spbstu.StudMess.repository.UserTaskRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserTaskService {

    private final UserTaskRepository userTaskRepository;
    private final UserService userService;
    private final TaskService taskService;

    @NonNull
    @Transactional(readOnly = true)
    public UserTaskEntity findByUserIdAndTaskId(@NonNull Long userId, @NonNull Long taskId) {
        return userTaskRepository.findByUserIdAndTaskId(userId, taskId).orElseThrow(() ->
                new NotFoundException(UserTaskEntity.class, "userId", "taskId", userId.toString(), taskId.toString()));
    }

    @NonNull
    @Transactional(readOnly = true)
    Page<UserTaskEntity> findAllByUserId(@NonNull Long userId, Pageable pageable) {
        return userTaskRepository.findAllByUserId(userId, pageable);
    }

    @NonNull
    @Transactional
    public UserTaskEntity create(@NonNull Long userId, @NonNull Long taskId) {
        final UserEntity user = userService.findById(userId);
        final TaskEntity task = taskService.findById(taskId);
        final UserTaskEntity userTask = new UserTaskEntity(user, task, false, null);

        return userTaskRepository.save(userTask);
    }

    @NonNull
    @Transactional
    public UserTaskEntity update(@NonNull Long userId, @NonNull Long taskId, @NonNull Boolean done,
                                @Nullable Integer score) {
        final UserTaskEntity userTask = userTaskRepository.findByUserIdAndTaskId(userId, taskId).orElseThrow(() ->
                new NotFoundException(UserTaskEntity.class, "userId", "taskId", userId.toString(), taskId.toString()));

        userTask.setDone(done);
        userTask.setScore(score);

        return userTaskRepository.save(userTask);
    }

    @Transactional
    public void delete(@NonNull Long userId, @NonNull Long taskId) {
        final UserTaskEntity userTask = userTaskRepository.findByUserIdAndTaskId(userId, taskId).orElseThrow(() ->
                new NotFoundException(UserTaskEntity.class, "userId", "taskId", userId.toString(), taskId.toString()));
        userTaskRepository.delete(userTask);
    }
}
