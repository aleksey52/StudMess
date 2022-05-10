package com.spbstu.StudMess.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Time;

@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "lessons")
public class LessonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    Integer semester;

    @NonNull
    Integer weekDay;

    @NonNull
    Time lessonTime;

    public LessonEntity(@NonNull Integer semester, @NonNull Integer weekDay, @NonNull Time lessonTime) {
        this.semester = semester;
        this.weekDay = weekDay;
        this.lessonTime = lessonTime;
    }
}
