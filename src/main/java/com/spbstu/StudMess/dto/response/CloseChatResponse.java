package com.spbstu.StudMess.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CloseChatResponse {

    @NonNull
    Long id;
    @NonNull
    String name;
    @NonNull
    Long initiatorId;
    @NonNull
    LocalDateTime creationDate;
    @Nullable
    MessageResponse lastMessage;
}
