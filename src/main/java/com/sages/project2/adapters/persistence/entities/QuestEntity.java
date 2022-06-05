package com.sages.project2.adapters.persistence.entities;

import com.sages.project2.domain.QuestDifficulty;
import com.sages.project2.domain.QuestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "quests")
public class QuestEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String questName;
    private String repoUrl;
    @Enumerated(EnumType.ORDINAL)
    private QuestStatus status;
    @Enumerated(EnumType.ORDINAL)
    private QuestDifficulty difficulty;
    private String topic;

}
