package com.sages.project2.adapters.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sages.project2.adapters.rest.dtos.QuestDto;
import com.sages.project2.adapters.rest.mappers.QuestRestMapper;
import com.sages.project2.domain.QuestDifficulty;
import com.sages.project2.domain.QuestStatus;
import com.sages.project2.domain.ports.in.QuestService;
import com.sages.project2.domain.ports.out.GitClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class QuestControllerIntegrationTest {

    private static final String QUESTS_ENDPOINT_URL = "/quests";
    private static final String QUESTS_ADMIN_ENDPOINT_URL = "/quests/admin";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private QuestService questService;
    @Autowired
    private QuestRestMapper questRestMapper;
    @MockBean
    GitClient gitClient;

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void questsAdminEndpoint_postQuestDto_shouldReturnQuestIdAndStatus201() throws Exception {
        // GIVEN
        var request = getQuestDto();
        var requestAsString = objectMapper.writeValueAsString(request);
        // WHEN
        Mockito.when(gitClient.createRepository(request.getQuestName())).thenReturn("https://some-mock-url");
        var result = mockMvc.perform(MockMvcRequestBuilders.post(QUESTS_ADMIN_ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestAsString))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        var contentAsString = result.getResponse().getContentAsString();
        var questId = objectMapper.readValue(contentAsString, Long.class);
        // THEN
        assertEquals(questId, 6);
    }

    private List<QuestDto> getQuestDtosList() {
        return List.of(
                QuestDto.builder().questName("1stQuestName").difficulty(QuestDifficulty.BEGINNER).content("1stQuestContent").status(QuestStatus.CREATED).build(),
                QuestDto.builder().questName("2ndQuestName").difficulty(QuestDifficulty.MASTER).content("2ndQuestContent").status(QuestStatus.CREATED).build(),
                QuestDto.builder().questName("3rdQuestName").difficulty(QuestDifficulty.BEGINNER).content("3rdQuestContent").status(QuestStatus.STARTED).build()
        );
    }

    private QuestDto getQuestDto() {
        return QuestDto.builder()
                .questName("1stQuestName")
                .difficulty(QuestDifficulty.BEGINNER)
                .content("1stQuestContent")
                .status(QuestStatus.CREATED)
                .build();
    }

}
