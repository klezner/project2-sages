package com.sages.project2.adapters.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

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
