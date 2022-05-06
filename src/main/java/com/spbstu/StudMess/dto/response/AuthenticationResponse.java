package com.spbstu.StudMess.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationResponse {

    @NonNull
    Long id;
    @NonNull
    String email;
    @NonNull
    String login;
    @NonNull
    String role;
}
