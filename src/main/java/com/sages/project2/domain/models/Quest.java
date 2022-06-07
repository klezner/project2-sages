package com.sages.project2.domain.models;

import com.sages.project2.domain.QuestDifficulty;
import com.sages.project2.domain.QuestStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Quest {

    Long id;
    String questName;
    String repoUrl;
    QuestStatus status;
    QuestDifficulty difficulty;
    String content;

}
