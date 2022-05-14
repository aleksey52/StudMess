package com.spbstu.StudMess.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
