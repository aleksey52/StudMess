package com.spbstu.StudMess.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentResponse {

    @NonNull
    Long id;
    @NonNull
    String content;
    @Nullable
    Long recipientId;
    @Nullable
    String recipientLastAndFirstName;
    @NonNull
    Long senderId;
    @NonNull
    String senderLastAndFirstName;
    @NonNull
    Long taskId;
    @NonNull
    LocalDateTime creationDate;
    @Nullable
    LocalDateTime updateDate;
}
