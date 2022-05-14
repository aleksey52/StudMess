package com.spbstu.StudMess.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ToDoTaskResponse {

    @NonNull
    Long id;
    @NonNull
    Long userId;
    @NonNull
    Long taskId;
    @NonNull
    Boolean done;
    @Nullable
    Integer score;
}
