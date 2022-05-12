package com.spbstu.StudMess.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherRequest {

    @NotBlank(message = "First name is required")
    String firstName;
    @NotBlank(message = "Middle name is required")
    String middleName;
    @NotBlank(message = "Last name is required")
    String lastName;
    @Nullable
    @Email(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    String email;
    @Nullable
    String phone;
}
