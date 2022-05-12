package com.spbstu.StudMess.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ToDoTaskRequest {

    @NotBlank(message = "Flag \"done\" is required")
    Boolean done;
    @Nullable
    Integer score;
}
