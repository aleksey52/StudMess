package com.spbstu.StudMess.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Time;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleResponse {

    @NonNull
    Long id;
    @NonNull
    Long lessonId;
    @NonNull
    Integer semester;
    @NonNull
    Integer weekDay;
    @NonNull
    Time lessonTime;
    @NonNull
    GroupResponse group;
    @NonNull
    SubjectResponse subject;
    @NonNull
    TeacherResponse teacher;
}
