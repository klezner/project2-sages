package com.sages.project2.adapters.persistence.entities;

import com.sages.project2.domain.QuestDifficulty;
import com.sages.project2.domain.QuestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Table(name = "quests")
public class QuestEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String questName;
    private String repoUrl;
    @Enumerated(EnumType.STRING)
    private QuestStatus status;
    @Enumerated(EnumType.STRING)
    private QuestDifficulty difficulty;
    private String content;
    @ManyToMany(mappedBy = "quests")
    private Set<UserEntity> users = new HashSet<>();

}
