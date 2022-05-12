package com.spbstu.StudMess.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TeacherResponse {

    @NonNull
    Long id;
    @NonNull
    String firstName;
    @NonNull
    String middleName;
    @NonNull
    String lastName;
    @Nullable
    String email;
    @Nullable
    String phone;
}
