package com.sages.project2.adapters.persistence.entities;

import com.sages.project2.domain.QuestDifficulty;
import com.sages.project2.domain.QuestStatus;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
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

}
