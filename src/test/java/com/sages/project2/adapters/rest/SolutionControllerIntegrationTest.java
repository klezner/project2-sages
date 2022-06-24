package com.sages.project2.adapters.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sages.project2.adapters.persistence.repositories.JpaSolutionRepository;
import com.sages.project2.adapters.rest.dtos.SolutionDto;
import com.sages.project2.domain.ports.out.DockerApiClient;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class SolutionControllerIntegrationTest {

    private static final String SOLUTIONS_ENDPOINT_URL = "/solutions";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JpaSolutionRepository jpaSolutionRepository;
    @MockBean
    private GitClient gitClient;
    @MockBean
    private DockerApiClient dockerClient;

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void saveQuest_shouldReturnQuestIdAndStatus200() throws Exception {
        // GIVEN
        var size = jpaSolutionRepository.findAll().size();
        var request = getSolutionDto();
        var requestAsString = objectMapper.writeValueAsString(request);
        // WHEN
        Mockito.when(dockerClient.checkSolution(request.getQuestName(), request.getUsername())).thenReturn("SUCCESS");

        var result = mockMvc.perform(MockMvcRequestBuilders.post(SOLUTIONS_ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestAsString))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var contentAsString = result.getResponse().getContentAsString();
        var solutionDto = objectMapper.readValue(contentAsString, SolutionDto.class);
        // THEN
        assertAll(
                () -> assertEquals(size, jpaSolutionRepository.findAll().size()),
                () -> assertEquals(request.getUserId(), solutionDto.getUserId()),
                () -> assertEquals(request.getUsername(), solutionDto.getUsername()),
                () -> assertEquals(request.getQuestName(), solutionDto.getQuestName()),
                () -> assertEquals(request.getSolution(), solutionDto.getSolution()),
                () -> assertEquals(request.isResult(), solutionDto.isResult())

        );
    }

    private SolutionDto getSolutionDto() {
        return SolutionDto.builder()
                .userId(1L)
                .username("Johnny")
                .questName("Quest name")
                .solution("Some solution")
                .result(true)
                .build();
    }

}
