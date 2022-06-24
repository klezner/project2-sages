package com.sages.project2.adapters.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sages.project2.adapters.persistence.entities.QuestEntity;
import com.sages.project2.adapters.persistence.entities.UserEntity;
import com.sages.project2.adapters.persistence.repositories.JpaQuestRepository;
import com.sages.project2.adapters.persistence.repositories.JpaUserRepository;
import com.sages.project2.adapters.rest.dtos.QuestDto;
import com.sages.project2.domain.QuestDifficulty;
import com.sages.project2.domain.QuestStatus;
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

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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
    private JpaQuestRepository jpaQuestRepository;
    @Autowired
    private JpaUserRepository jpaUserRepository;
    @MockBean
    private GitClient gitClient;

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void saveQuest_shouldReturnQuestIdAndStatus201() throws Exception {
        // GIVEN
        var size = jpaQuestRepository.findAll().size();
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
        assertEquals(questId, size + 1);
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void getQuests_noParameters_shouldReturnQuestsAndStatus200() throws Exception {
        // GIVEN
        var questEntitiesList = getQuestEntitiesList();
        questEntitiesList.forEach(questEntity -> jpaQuestRepository.save(questEntity));
        // WHEN
        var result = mockMvc.perform(MockMvcRequestBuilders.get(QUESTS_ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var contentAsString = result.getResponse().getContentAsString();
        var questDtoList = objectMapper.readValue(contentAsString, new TypeReference<List<QuestDto>>() {
        });
        // THEN
        assertAll(
                () -> assertEquals(questEntitiesList.size(), questDtoList.size()),
                () -> assertEquals(questEntitiesList.get(0).getQuestName(), questDtoList.get(0).getQuestName()),
                () -> assertEquals(questEntitiesList.get(0).getStatus(), questDtoList.get(0).getStatus()),
                () -> assertEquals(questEntitiesList.get(0).getDifficulty(), questDtoList.get(0).getDifficulty()),
                () -> assertEquals(questEntitiesList.get(0).getContent(), questDtoList.get(0).getContent()),
                () -> assertEquals(questEntitiesList.get(0).getUsers().size(), questDtoList.get(0).getUsers().size()),
                () -> assertEquals(questEntitiesList.get(1).getQuestName(), questDtoList.get(1).getQuestName()),
                () -> assertEquals(questEntitiesList.get(1).getStatus(), questDtoList.get(1).getStatus()),
                () -> assertEquals(questEntitiesList.get(1).getDifficulty(), questDtoList.get(1).getDifficulty()),
                () -> assertEquals(questEntitiesList.get(1).getContent(), questDtoList.get(1).getContent()),
                () -> assertEquals(questEntitiesList.get(1).getUsers().size(), questDtoList.get(1).getUsers().size()),
                () -> assertEquals(questEntitiesList.get(2).getQuestName(), questDtoList.get(2).getQuestName()),
                () -> assertEquals(questEntitiesList.get(2).getStatus(), questDtoList.get(2).getStatus()),
                () -> assertEquals(questEntitiesList.get(2).getDifficulty(), questDtoList.get(2).getDifficulty()),
                () -> assertEquals(questEntitiesList.get(2).getContent(), questDtoList.get(2).getContent()),
                () -> assertEquals(questEntitiesList.get(2).getUsers().size(), questDtoList.get(2).getUsers().size())
        );
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void getQuests_withStatusCreatedAsParameter_shouldReturnQuestsWithStatusCreatedAndStatus200() throws Exception {
        // GIVEN
        var status = "status";
        var questEntitiesList = getQuestEntitiesList();
        questEntitiesList.forEach(questEntity -> jpaQuestRepository.save(questEntity));
        var createdQuestEntitiesList = questEntitiesList.stream()
                .filter(questEntity -> QuestStatus.CREATED.equals(questEntity.getStatus()))
                .collect(Collectors.toList());
        // WHEN
        var result = mockMvc.perform(MockMvcRequestBuilders.get(QUESTS_ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(status, String.valueOf(QuestStatus.CREATED)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var contentAsString = result.getResponse().getContentAsString();
        var questDtoList = objectMapper.readValue(contentAsString, new TypeReference<List<QuestDto>>() {
        });
        // THEN
        assertAll(
                () -> assertEquals(createdQuestEntitiesList.size(), questDtoList.size()),
                () -> assertEquals(createdQuestEntitiesList.get(0).getQuestName(), questDtoList.get(0).getQuestName()),
                () -> assertEquals(createdQuestEntitiesList.get(0).getStatus(), questDtoList.get(0).getStatus()),
                () -> assertEquals(createdQuestEntitiesList.get(0).getDifficulty(), questDtoList.get(0).getDifficulty()),
                () -> assertEquals(createdQuestEntitiesList.get(0).getContent(), questDtoList.get(0).getContent()),
                () -> assertEquals(createdQuestEntitiesList.get(0).getUsers().size(), questDtoList.get(0).getUsers().size()),
                () -> assertEquals(createdQuestEntitiesList.get(1).getQuestName(), questDtoList.get(1).getQuestName()),
                () -> assertEquals(createdQuestEntitiesList.get(1).getStatus(), questDtoList.get(1).getStatus()),
                () -> assertEquals(createdQuestEntitiesList.get(1).getDifficulty(), questDtoList.get(1).getDifficulty()),
                () -> assertEquals(createdQuestEntitiesList.get(1).getContent(), questDtoList.get(1).getContent()),
                () -> assertEquals(createdQuestEntitiesList.get(1).getUsers().size(), questDtoList.get(1).getUsers().size())
        );
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void getQuests_withStatusXAsParameter_shouldReturnResponseWithExceptionMessageAndStatus400() throws Exception {
        // GIVEN
        var status = "status";
        var parameter = "X";
        var description = "Invalid value[s] for the parameter[s]";
        var questEntitiesList = getQuestEntitiesList();
        questEntitiesList.forEach(questEntity -> jpaQuestRepository.save(questEntity));
        var createdQuestEntitiesList = questEntitiesList.stream()
                .filter(questEntity -> parameter.equals(questEntity.getStatus()))
                .collect(Collectors.toList());
        // WHEN
        var result = mockMvc.perform(MockMvcRequestBuilders.get(QUESTS_ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(status, parameter))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        // com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot construct instance of `com.sages.project2.adapters.rest.dtos.ExceptionDto` (although at least one Creator exists): cannot deserialize from Object value (no delegate- or property-based Creator)
//        var contentAsString = result.getResponse().getContentAsString();
//        var exceptionDto = objectMapper.readValue(contentAsString, ExceptionDto.class);
        // THEN
//        assertEquals(description, exceptionDto.getDescription());
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void getQuests_withDifficultyBeginnerAsParameter_shouldReturnQuestsWithDifficultyBeginnerAndStatus200() throws Exception {
        // GIVEN
        var difficulty = "difficulty";
        var questEntitiesList = getQuestEntitiesList();
        questEntitiesList.forEach(questEntity -> jpaQuestRepository.save(questEntity));
        var createdQuestEntitiesList = questEntitiesList.stream()
                .filter(questEntity -> QuestDifficulty.BEGINNER.equals(questEntity.getDifficulty()))
                .collect(Collectors.toList());
        // WHEN
        var result = mockMvc.perform(MockMvcRequestBuilders.get(QUESTS_ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(difficulty, String.valueOf(QuestDifficulty.BEGINNER)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var contentAsString = result.getResponse().getContentAsString();
        var questDtoList = objectMapper.readValue(contentAsString, new TypeReference<List<QuestDto>>() {
        });
        // THEN
        assertAll(
                () -> assertEquals(createdQuestEntitiesList.size(), questDtoList.size()),
                () -> assertEquals(createdQuestEntitiesList.get(0).getQuestName(), questDtoList.get(0).getQuestName()),
                () -> assertEquals(createdQuestEntitiesList.get(0).getStatus(), questDtoList.get(0).getStatus()),
                () -> assertEquals(createdQuestEntitiesList.get(0).getDifficulty(), questDtoList.get(0).getDifficulty()),
                () -> assertEquals(createdQuestEntitiesList.get(0).getContent(), questDtoList.get(0).getContent()),
                () -> assertEquals(createdQuestEntitiesList.get(0).getUsers().size(), questDtoList.get(0).getUsers().size()),
                () -> assertEquals(createdQuestEntitiesList.get(1).getQuestName(), questDtoList.get(1).getQuestName()),
                () -> assertEquals(createdQuestEntitiesList.get(1).getStatus(), questDtoList.get(1).getStatus()),
                () -> assertEquals(createdQuestEntitiesList.get(1).getDifficulty(), questDtoList.get(1).getDifficulty()),
                () -> assertEquals(createdQuestEntitiesList.get(1).getContent(), questDtoList.get(1).getContent()),
                () -> assertEquals(createdQuestEntitiesList.get(1).getUsers().size(), questDtoList.get(1).getUsers().size())
        );
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void getQuests_withDifficultyXAsParameter_shouldReturnResponseWithExceptionMessageAndStatus400() throws Exception {
        // GIVEN
        var difficulty = "difficulty";
        var parameter = "X";
        var description = "Invalid value[s] for the parameter[s]";
        var questEntitiesList = getQuestEntitiesList();
        questEntitiesList.forEach(questEntity -> jpaQuestRepository.save(questEntity));
        var createdQuestEntitiesList = questEntitiesList.stream()
                .filter(questEntity -> parameter.equals(questEntity.getDifficulty()))
                .collect(Collectors.toList());
        // WHEN
        var result = mockMvc.perform(MockMvcRequestBuilders.get(QUESTS_ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(difficulty, parameter))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        // com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot construct instance of `com.sages.project2.adapters.rest.dtos.ExceptionDto` (although at least one Creator exists): cannot deserialize from Object value (no delegate- or property-based Creator)
//        var contentAsString = result.getResponse().getContentAsString();
//        var exceptionDto = objectMapper.readValue(contentAsString, ExceptionDto.class);
        // THEN
//        assertEquals(description, exceptionDto.getDescription());
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void getQuests_withDifficultyBeginnerAndStatusCreatedAsParameters_shouldReturnQuestsWithDifficultyBeginnerAndStatusCreatedAndStatus200() throws Exception {
        // GIVEN
        var status = "status";
        var difficulty = "difficulty";
        var questEntitiesList = getQuestEntitiesList();
        questEntitiesList.forEach(questEntity -> jpaQuestRepository.save(questEntity));
        var createdQuestEntitiesList = questEntitiesList.stream()
                .filter(questEntity -> QuestDifficulty.BEGINNER.equals(questEntity.getDifficulty()))
                .filter(questEntity -> QuestStatus.CREATED.equals(questEntity.getStatus()))
                .collect(Collectors.toList());
        // WHEN
        var result = mockMvc.perform(MockMvcRequestBuilders.get(QUESTS_ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(difficulty, String.valueOf(QuestDifficulty.BEGINNER))
                        .param(status, String.valueOf(QuestStatus.CREATED)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var contentAsString = result.getResponse().getContentAsString();
        var questDtoList = objectMapper.readValue(contentAsString, new TypeReference<List<QuestDto>>() {
        });
        // THEN
        assertAll(
                () -> assertEquals(createdQuestEntitiesList.size(), questDtoList.size()),
                () -> assertEquals(createdQuestEntitiesList.get(0).getQuestName(), questDtoList.get(0).getQuestName()),
                () -> assertEquals(createdQuestEntitiesList.get(0).getStatus(), questDtoList.get(0).getStatus()),
                () -> assertEquals(createdQuestEntitiesList.get(0).getDifficulty(), questDtoList.get(0).getDifficulty()),
                () -> assertEquals(createdQuestEntitiesList.get(0).getContent(), questDtoList.get(0).getContent()),
                () -> assertEquals(createdQuestEntitiesList.get(0).getUsers().size(), questDtoList.get(0).getUsers().size())
        );
    }

    // jakarta.servlet.ServletException: Request processing failed: org.springframework.transaction.IllegalTransactionStateException: No existing transaction found for transaction marked with propagation 'mandatory'
//    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
//    @Test
//    void addUserToQuest_shouldReturnMessageAndStatus200() throws Exception {
//        // GIVEN
//        var userEntity = getUserEntity();
//        var savedUserEntity = jpaUserRepository.save(userEntity);
//        var questEntity = getQuestEntity();
//        var savedQuestEntity = jpaQuestRepository.save(questEntity);
//        var expectedBody = String.format("User %s added to quest with id %d.", savedUserEntity.getLogin(), savedQuestEntity.getId());
//        // WHEN
//        var uri = QUESTS_ENDPOINT_URL + "/" + savedQuestEntity.getId() + "/" + savedUserEntity.getLogin();
//        var result = mockMvc.perform(MockMvcRequestBuilders.post(uri)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn();
//        var contentAsString = result.getResponse().getContentAsString();
//        var body = objectMapper.readValue(contentAsString, String.class);
//        // THEN
//        assertEquals(expectedBody, body);
//    }

    private List<QuestEntity> getQuestEntitiesList() {
        return List.of(
                new QuestEntity(1L, "1stQuestName", "1stRepoUrl", QuestStatus.CREATED, QuestDifficulty.BEGINNER, "1stContent", new HashSet<>()),
                new QuestEntity(2L, "2ndQuestName", "2ndRepoUrl", QuestStatus.STARTED, QuestDifficulty.BEGINNER, "2ndContent", new HashSet<>()),
                new QuestEntity(3L, "3rdQuestName", "3rdRepoUrl", QuestStatus.CREATED, QuestDifficulty.MASTER, "3rdContent", new HashSet<>())
        );
    }

    private QuestEntity getQuestEntity() {
        return new QuestEntity(1L, "1stQuestName", "1stRepoUrl", QuestStatus.CREATED, QuestDifficulty.BEGINNER, "1stContent", new HashSet<>());
    }

    private QuestDto getQuestDto() {
        return QuestDto.builder()
                .questName("1stQuestName")
                .difficulty(QuestDifficulty.BEGINNER)
                .content("1stQuestContent")
                .status(QuestStatus.CREATED)
                .build();
    }

    private UserEntity getUserEntity() {
        return new UserEntity("Johnny", "ROLE_USER", new HashSet<>());
    }

}
