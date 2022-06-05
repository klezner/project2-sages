package com.sages.project2.adapters.rest.dtos;

import com.sages.project2.domain.QuestDifficulty;
import com.sages.project2.domain.QuestStatus;
import lombok.Data;

@Data
public class QuestDto {

    private String questName;
    private QuestDifficulty difficulty;
    private String topic;

}
