package com.spbstu.StudMess.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationRequest {

    @NotBlank(message = "Login is required")
    @Size(min = 8, max = 45, message = "The login must be from 8 to 45 characters")
    String login;
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 45, message = "The password must be from 8 to 45 characters")
    String password;
    @NotBlank(message = "First name is required")
    String firstName;
    @NotBlank(message = "Middle name is required")
    String middleName;
    @NotBlank(message = "Last name is required")
    String lastName;
    @NotBlank(message = "Group is required")
    String group;
    @Email(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    String email;
    @Nullable
    String phone;
}
