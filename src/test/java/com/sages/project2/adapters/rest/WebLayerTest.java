package com.sages.project2.adapters.rest;

import com.sages.project2.domain.ports.in.QuestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = QuestController.class)
class WebLayerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuestService questService;

    @Test
    @WithMockUser
    public void checkQuestWhenValidInputShouldReturnOk() throws Exception {
        mockMvc.perform(get("/quests/1/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("failed"));
    }

}