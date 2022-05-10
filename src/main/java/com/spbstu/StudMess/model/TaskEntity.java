package com.spbstu.StudMess.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    ScheduleEntity schedule;

    @NonNull
    String name;
    @Nullable
    String context;
    @Nullable
    LocalDateTime deadline;

    @Fetch(FetchMode.SUBSELECT)
    @Builder.Default
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    List<CommentEntity> comments = new ArrayList<>();

    public TaskEntity(@NonNull ScheduleEntity schedule, @NonNull String name, @Nullable String context,
                      @Nullable LocalDateTime deadline) {
        this.schedule = schedule;
        this.name = name;
        this.context = context;
        this.deadline = deadline;
    }
}
