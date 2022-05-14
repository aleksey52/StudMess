package com.spbstu.StudMess.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OpenChatResponse {

    @NonNull
    Long id;
    @NonNull
    String name;
    @NonNull
    Long initiatorId;
    @NonNull
    LocalDateTime creationDate;
}
