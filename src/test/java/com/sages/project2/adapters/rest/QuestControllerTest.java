package com.sages.project2.adapters.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sages.project2.adapters.rest.dtos.ExceptionDto;
import com.sages.project2.adapters.rest.dtos.QuestDto;
import com.sages.project2.adapters.rest.mappers.QuestRestMapper;
import com.sages.project2.domain.QuestDifficulty;
import com.sages.project2.domain.QuestStatus;
import com.sages.project2.domain.models.Quest;
import com.sages.project2.domain.ports.in.QuestService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest
class QuestControllerTest {

    private static final String QUESTS_ENDPOINT_URL = "/quests";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private QuestService questService;
    @MockBean
    private QuestRestMapper questRestMapper;

    @Test
    void questsEndpoint_noDifficultyParameter_shouldReturn200AndListOfQuests() throws Exception {
        // GIVEN
        var quests = List.of(
                Quest.builder().questName("1stQuestName").difficulty(QuestDifficulty.BEGINNER).content("1stQuestContent").status(QuestStatus.CREATED).repoUrl("1stRepoUrl").build(),
                Quest.builder().questName("2ndQuestName").difficulty(QuestDifficulty.MASTER).content("2ndQuestContent").status(QuestStatus.CREATED).repoUrl("2ndRepoUrl").build(),
                Quest.builder().questName("3rdQuestName").difficulty(QuestDifficulty.BEGINNER).content("3rdQuestContent").status(QuestStatus.STARTED).repoUrl("3rdRepoUrl").build()
        );
        var dtos = List.of(
                QuestDto.builder().questName("1stQuestName").difficulty(QuestDifficulty.BEGINNER).content("1stQuestContent").build(),
                QuestDto.builder().questName("2ndQuestName").difficulty(QuestDifficulty.MASTER).content("2ndQuestContent").build(),
                QuestDto.builder().questName("3rdQuestName").difficulty(QuestDifficulty.BEGINNER).content("3rdQuestContent").build()
        );
        // WHEN
        Mockito.when(questService.findAllQuests()).thenReturn(quests);
        Mockito.when(questRestMapper.toDto(quests)).thenReturn(dtos);

        var result = mockMvc.perform(MockMvcRequestBuilders.get(QUESTS_ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var contentAsString = result.getResponse().getContentAsString();
        var content = objectMapper.readValue(contentAsString, new TypeReference<List<QuestDto>>() {
        });
        // THEN
        assertAll(
                () -> assertEquals(3, content.size()),
                () -> assertEquals("1stQuestName", content.get(0).getQuestName()),
                () -> assertEquals(QuestDifficulty.BEGINNER, content.get(0).getDifficulty()),
                () -> assertEquals("1stQuestContent", content.get(0).getContent()),
                () -> assertEquals("2ndQuestName", content.get(1).getQuestName()),
                () -> assertEquals(QuestDifficulty.MASTER, content.get(1).getDifficulty()),
                () -> assertEquals("2ndQuestContent", content.get(1).getContent()),
                () -> assertEquals("3rdQuestName", content.get(2).getQuestName()),
                () -> assertEquals(QuestDifficulty.BEGINNER, content.get(2).getDifficulty()),
                () -> assertEquals("3rdQuestContent", content.get(2).getContent())
        );
    }

    @Test
    void questsEndpoint_withValidDifficultyParameter_shouldReturn200AndListOfValidQuests() throws Exception {
        // GIVEN
        var quests = List.of(
                Quest.builder().questName("1stQuestName").difficulty(QuestDifficulty.BEGINNER).content("1stQuestContent").status(QuestStatus.CREATED).repoUrl("1stRepoUrl").build(),
                Quest.builder().questName("2ndQuestName").difficulty(QuestDifficulty.MASTER).content("2ndQuestContent").status(QuestStatus.CREATED).repoUrl("2ndRepoUrl").build(),
                Quest.builder().questName("3rdQuestName").difficulty(QuestDifficulty.BEGINNER).content("3rdQuestContent").status(QuestStatus.STARTED).repoUrl("3rdRepoUrl").build()
        );
        var dtos = List.of(
                QuestDto.builder().questName("1stQuestName").difficulty(QuestDifficulty.BEGINNER).content("1stQuestContent").build(),
                QuestDto.builder().questName("2ndQuestName").difficulty(QuestDifficulty.MASTER).content("2ndQuestContent").build(),
                QuestDto.builder().questName("3rdQuestName").difficulty(QuestDifficulty.BEGINNER).content("3rdQuestContent").build()
        );
        // WHEN
        Mockito.when(questService.findAllQuests()).thenReturn(quests);
        Mockito.when(questRestMapper.toDto(quests)).thenReturn(dtos);

        var result = mockMvc.perform(MockMvcRequestBuilders.get(QUESTS_ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("difficulty", "BEGINNER"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var contentAsString = result.getResponse().getContentAsString();
        var content = objectMapper.readValue(contentAsString, new TypeReference<List<QuestDto>>() {
        });
        // THEN
        assertAll(
                () -> assertEquals(2, content.size()),
                () -> assertEquals("1stQuestName", content.get(0).getQuestName()),
                () -> assertEquals(QuestDifficulty.BEGINNER, content.get(0).getDifficulty()),
                () -> assertEquals("1stQuestContent", content.get(0).getContent()),
                () -> assertEquals("3rdQuestName", content.get(2).getQuestName()),
                () -> assertEquals(QuestDifficulty.BEGINNER, content.get(2).getDifficulty()),
                () -> assertEquals("3rdQuestContent", content.get(2).getContent())
        );
    }

    @Test
    void questsEndpoint_withInvalidDifficultyParameter_shouldReturn400AndExceptionResponse() throws Exception {
        // GIVEN
        var quests = List.of(
                Quest.builder().questName("1stQuestName").difficulty(QuestDifficulty.BEGINNER).content("1stQuestContent").status(QuestStatus.CREATED).repoUrl("1stRepoUrl").build(),
                Quest.builder().questName("2ndQuestName").difficulty(QuestDifficulty.MASTER).content("2ndQuestContent").status(QuestStatus.CREATED).repoUrl("2ndRepoUrl").build(),
                Quest.builder().questName("3rdQuestName").difficulty(QuestDifficulty.BEGINNER).content("3rdQuestContent").status(QuestStatus.STARTED).repoUrl("3rdRepoUrl").build()
        );
        var dtos = List.of(
                QuestDto.builder().questName("1stQuestName").difficulty(QuestDifficulty.BEGINNER).content("1stQuestContent").build(),
                QuestDto.builder().questName("2ndQuestName").difficulty(QuestDifficulty.MASTER).content("2ndQuestContent").build(),
                QuestDto.builder().questName("3rdQuestName").difficulty(QuestDifficulty.BEGINNER).content("3rdQuestContent").build()
        );
        String description = "Invalid value for the difficulty parameter";
        // WHEN
        Mockito.when(questService.findAllQuests()).thenReturn(quests);
        Mockito.when(questRestMapper.toDto(quests)).thenReturn(dtos);

        var result = mockMvc.perform(MockMvcRequestBuilders.get(QUESTS_ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var contentAsString = result.getResponse().getContentAsString();
        var content = objectMapper.readValue(contentAsString, ExceptionDto.class);
        // THEN
        assertEquals(description, content.getDescription());
    }

}
