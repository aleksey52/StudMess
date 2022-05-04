package com.spbstu.StudMess.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "messages")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_id")
    UserEntity recipient;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_id")
    UserEntity sender;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    ChatEntity chat;

    @NonNull
    @Column(name = "created_at")
    LocalDateTime creationDate;

    @Column(name = "updated_at")
    LocalDateTime updateDate;
}
