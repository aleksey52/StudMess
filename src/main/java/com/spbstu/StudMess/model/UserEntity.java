package com.spbstu.StudMess.model;

import com.spbstu.StudMess.model.enums.Role;
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
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    String login;

    @NonNull
    String password;

    @NonNull
    @Enumerated(EnumType.STRING)
    Role role;

    @NonNull
    String firstName;

    @NonNull
    String middleName;

    @NonNull
    String lastName;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    GroupEntity group;

    @NonNull
    String email;
    String phone;

    boolean enabled;
    String verificationCode;

    @Fetch(FetchMode.SUBSELECT)
    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "users_chats",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
    List<ChatEntity> chats = new ArrayList<>();
}
