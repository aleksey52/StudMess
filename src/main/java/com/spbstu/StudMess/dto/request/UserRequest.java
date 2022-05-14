package com.spbstu.StudMess.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRequest {

    @NotBlank(message = "Login is required")
    String login;
    @NotBlank(message = "First Name is required")
    String firstName;
    @NotBlank(message = "Middle Name is required")
    String middleName;
    @NotBlank(message = "Last Name is required")
    String lastName;
    @NotBlank(message = "Group is required")
    String group;
    @Email(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    String email;
    @Nullable
    String phone;
}
