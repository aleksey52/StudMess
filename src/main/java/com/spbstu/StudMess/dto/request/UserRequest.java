package com.spbstu.StudMess.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRequest {

    String login;
    String firstName;
    String middleName;
    String lastName;
    String group;
    String email;
    String phone;
}
