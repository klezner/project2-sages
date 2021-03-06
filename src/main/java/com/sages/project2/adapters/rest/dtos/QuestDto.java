package com.sages.project2.adapters.rest.dtos;

import com.sages.project2.adapters.persistence.entities.UserEntity;
import com.sages.project2.domain.QuestDifficulty;
import com.sages.project2.domain.QuestStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class QuestDto {

    private String questName;
    private QuestDifficulty difficulty;
    private QuestStatus status;
    private String content;
    private Set<UserEntity> users;
}
