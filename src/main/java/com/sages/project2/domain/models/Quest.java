package com.sages.project2.domain.models;

import com.sages.project2.domain.QuestDifficulty;
import com.sages.project2.domain.QuestStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Setter
@Getter
@Builder
public class Quest {

    String questName;
    String repoUrl;
    QuestStatus status;
    QuestDifficulty difficulty;
    String content;

}
