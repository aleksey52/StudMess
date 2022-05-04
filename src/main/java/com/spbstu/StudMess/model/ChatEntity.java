package com.spbstu.StudMess.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
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
@Table(name = "chats")
public class ChatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id")
    UserEntity initiator;

    @Fetch(FetchMode.SUBSELECT)
    @Builder.Default
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    List<MessageEntity> messages = new ArrayList<>();

    @Fetch(FetchMode.SUBSELECT)
    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "users_chats",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    List<UserEntity> users = new ArrayList<>();
}
