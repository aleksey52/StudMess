package com.spbstu.StudMess.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

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
//    @Nullable
//    List<MessageResponse> messages;
//    @NonNull
//    List<UserResponse> users;
}
