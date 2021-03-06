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
@Table(name = "messages", schema = "studmess")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    String content;

    @Nullable
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

    @Nullable
    @Column(name = "updated_at")
    LocalDateTime updateDate;

    @Fetch(FetchMode.SUBSELECT)
    @Builder.Default
    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    List<AttachmentEntity> attachments = new ArrayList<>();

    public MessageEntity(@NonNull String content, @NonNull UserEntity sender, @NonNull ChatEntity chat) {
        this.content = content;
        this.sender = sender;
        this.chat = chat;
    }
}
