package com.spbstu.StudMess.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import java.sql.Time;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScheduleRequest {

    @NotBlank(message = "Semester is required")
    Integer semester;
    @NotBlank(message = "Week day is required")
    Integer weekDay;
    @NotBlank(message = "Lesson time is required")
    Time lessonTime;
    @NotBlank(message = "Group is required")
    Long groupId;
    @NotBlank(message = "Subject is required")
    Long subjectId;
    @NotBlank(message = "Teacher is required")
    Long teacherId;
}
