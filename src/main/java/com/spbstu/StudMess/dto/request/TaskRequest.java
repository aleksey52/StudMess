package com.spbstu.StudMess.dto.request;

import com.spbstu.StudMess.model.ScheduleEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskRequest {

    @NotBlank(message = "Title is required")
    String name;
    @Nullable
    @Size(max = 5000, message = "The description can be up to 5000 characters long.")
    String context;
    @Nullable
    LocalDateTime deadline;
}
