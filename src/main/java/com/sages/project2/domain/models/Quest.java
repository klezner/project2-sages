package com.sages.project2.domain.models;

import com.sages.project2.domain.QuestDifficulty;
import com.sages.project2.domain.QuestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.util.Set;

@Setter
@Getter
@Builder
public class Quest {

    String questName;
    String repoUrl;
    QuestStatus status;
    QuestDifficulty difficulty;
    String content;
    Set<User> users;

}
