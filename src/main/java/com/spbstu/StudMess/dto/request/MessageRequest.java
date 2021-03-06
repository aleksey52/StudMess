package com.spbstu.StudMess.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageRequest {

    @NotBlank(message = "Message is required")
    @Size(max = 5000, message = "The message can be up to 5000 characters long.")
    String content;
    @Nullable
    Long recipientId;
    @Nullable
    @Size(max = 10, message = "The maximum number of attachments is 10")
    List<AttachmentRequest> attachments;
}
