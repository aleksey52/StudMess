package com.spbstu.StudMess.dto.response;

import com.spbstu.StudMess.model.ScheduleEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskResponse {

    @NonNull
    Long id;
    @NonNull
    Long scheduleId;
    @NonNull
    String name;
    @Nullable
    String context;
    @Nullable
    LocalDateTime deadline;
}
