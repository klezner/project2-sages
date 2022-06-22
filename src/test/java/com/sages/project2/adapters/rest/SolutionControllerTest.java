package com.sages.project2.adapters.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sages.project2.adapters.rest.dtos.SolutionDto;
import com.sages.project2.adapters.rest.mappers.SolutionRestMapper;
import com.sages.project2.domain.models.Solution;
import com.sages.project2.domain.ports.in.SolutionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest
@ContextConfiguration(classes = SolutionController.class)
class SolutionControllerTest {

    private static final String SOLUTIONS_ENDPOINT_URL = "/solutions";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private SolutionService solutionService;
    @MockBean
    private SolutionRestMapper solutionRestMapper;

    @Test
    void solutionsEndpoint_addSolution_shouldReturnAndSolutionDto() throws Exception {
        // GIVEN
        var solution = Solution.builder().userId(1L).username("Johnny").questName("Quest name").solution("Some solution").result(true).build();
        var solutionFromDb = Solution.builder().userId(1L).username("Johnny").questName("Quest name").solution("Some solution").result(true).build();
        var dto = SolutionDto.builder().userId(1L).username("Johnny").questName("Quest name").solution("Some solution").result(true).build();
        // WHEN
        Mockito.when(solutionRestMapper.toDomain(Mockito.any(SolutionDto.class))).thenReturn(solution);
        Mockito.when(solutionService.addSolution(solution)).thenReturn(solutionFromDb);
        Mockito.when(solutionRestMapper.toDto(solutionFromDb)).thenReturn(dto);

        var result = mockMvc.perform(MockMvcRequestBuilders.post(SOLUTIONS_ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        var contentAsString = result.getResponse().getContentAsString();
        var content = objectMapper.readValue(contentAsString, SolutionDto.class);
        // THEN
        assertAll(
                () -> assertEquals(dto.getUserId(), content.getUserId()),
                () -> assertEquals(dto.getUsername(), content.getUsername()),
                () -> assertEquals(dto.getQuestName(), content.getQuestName()),
                () -> assertEquals(dto.getSolution(), content.getSolution()),
                () -> assertEquals(dto.isResult(), content.isResult())
        );
    }

}
