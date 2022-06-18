package com.sages.project2.adapters.rest.dtos;

import com.sages.project2.adapters.persistence.entities.UserEntity;
import com.sages.project2.domain.QuestDifficulty;
import lombok.Data;

import java.util.Set;

@Data
public class QuestDto {

    private String questName;
    private QuestDifficulty difficulty;
    private String content;
    private Set<UserEntity> users;
}
