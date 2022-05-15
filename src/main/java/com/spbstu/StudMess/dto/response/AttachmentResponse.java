package com.spbstu.StudMess.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttachmentResponse {

    @NonNull
    Long id;
    @NonNull
    String name;
    @NonNull
    String url;
    @NonNull
    Integer messageId;
}
