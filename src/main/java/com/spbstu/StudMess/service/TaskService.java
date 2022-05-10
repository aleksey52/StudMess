package com.spbstu.StudMess.service;

import com.spbstu.StudMess.exception.NotFoundException;
import com.spbstu.StudMess.model.ScheduleEntity;
import com.spbstu.StudMess.model.TaskEntity;
import com.spbstu.StudMess.repository.TaskRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ScheduleService scheduleService;

    @NonNull
    @Transactional(readOnly = true)
    public TaskEntity findById(@NonNull Long id) {
        return taskRepository.findById(id).orElseThrow(() ->
                new NotFoundException(TaskEntity.class, "id", id.toString()));
    }

    @NonNull
    @Transactional(readOnly = true)
    public TaskEntity findByName(@NonNull String name) {
        return taskRepository.findByName(name).orElseThrow(() ->
                new NotFoundException(TaskEntity.class, "name", name));
    }

    @NonNull
    @Transactional(readOnly = true)
    public Page<TaskEntity> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @NonNull
    @Transactional
    public TaskEntity create(@NonNull Long scheduleId, @NonNull String name, @Nullable String context,
                             @Nullable LocalDateTime deadline) {
        final ScheduleEntity scheduleEntity = scheduleService.findById(scheduleId);
        final TaskEntity taskEntity = new TaskEntity(scheduleEntity, name, context, deadline);

        return taskRepository.save(taskEntity);
    }

    @NonNull
    @Transactional
    public TaskEntity update(@NonNull Long taskId, @NonNull Long scheduleId, @NonNull String name,
                             @Nullable String context, @Nullable LocalDateTime deadline) {
        final ScheduleEntity scheduleEntity = scheduleService.findById(scheduleId);
        final TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(() ->
                new NotFoundException(TaskEntity.class, taskId.toString()));

        taskEntity.setSchedule(scheduleEntity);
        taskEntity.setName(name);
        taskEntity.setContext(context);
        taskEntity.setDeadline(deadline);

        return taskRepository.save(taskEntity);
    }

    @Transactional
    public void delete(@NonNull Long id) {
        final TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(() ->
                new NotFoundException(TaskEntity.class, id.toString()));
        taskRepository.delete(taskEntity);
    }
}
