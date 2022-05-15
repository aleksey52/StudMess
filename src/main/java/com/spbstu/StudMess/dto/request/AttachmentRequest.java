package com.spbstu.StudMess.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttachmentRequest {

    @NotBlank(message = "Name is required")
    String name;
    @NotBlank(message = "URL is required")
    String url;
}
