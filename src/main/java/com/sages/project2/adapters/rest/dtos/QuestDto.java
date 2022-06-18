package com.sages.project2.adapters.rest.dtos;

import com.sages.project2.domain.QuestDifficulty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class QuestDto {

    private String questName;
    private QuestDifficulty difficulty;
    private String content;

}
