package com.sages.project2.adapters.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "users")
public class UserEntity {

    @Id
    private String login;
    private String role;

    @ManyToMany
    @JoinTable(
            name= "users_quests",
            joinColumns = @JoinColumn(name="user_login"),
            inverseJoinColumns = @JoinColumn(name="quest_id")
    )
    private Set<QuestEntity> quests = new HashSet<>();

}
