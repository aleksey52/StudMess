package com.spbstu.StudMess.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserResponse {

    @NonNull
    Long id;
    @NonNull
    String login;
    @NonNull
    String firstName;
    @NonNull
    String middleName;
    @NonNull
    String lastName;
    @NonNull
    String group;
    @NonNull
    String email;
    String phone;
}
