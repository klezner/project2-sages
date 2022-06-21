package com.sages.project2.domain.services;

import com.sages.project2.domain.QuestDifficulty;
import com.sages.project2.domain.QuestStatus;
import com.sages.project2.domain.models.Quest;
import com.sages.project2.domain.models.Solution;
import com.sages.project2.domain.ports.out.DockerApiClient;
import com.sages.project2.domain.ports.out.GitClient;
import com.sages.project2.domain.ports.out.QuestRepository;
import com.sages.project2.domain.ports.out.SolutionRepository;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SolutionManagerTest {

    @Mock
    private QuestRepository questRepository;
    @Mock
    private GitClient gitClient;
    @Mock
    private DockerApiClient dockerClient;
    @InjectMocks
    private SolutionManager solutionManager;
    @Mock
    SolutionRepository solutionRepository;

    @Test
    void context() {
    }

    private Quest getStubbedQuest(String questName) {
        return Quest.builder()
                .questName(questName)
                .content("Write a main class with method that prints Hello World to the console!")
                .status(QuestStatus.CREATED)
                .repoUrl("mock://test.com")
                .difficulty(QuestDifficulty.BEGINNER)
                .build();
    }

    private Solution getUsersSolution(String username) {
        return Solution.builder()
                .questId(1L)
                .username(username)
                .questId(1L)
                .solution("public class Main { public static void main(String[] args) { System.out.print(\"Hello World!\"); } }")
                .result(false)
                .build();
    }

    @Test
    void givenQuestWithId1LExists_and_solutionIsCorrect_andBranchExists_returnSolutionWithResultTrue() throws IOException, DockerException, DockerCertificateException, InterruptedException {

        String questName = "hello-world";
        String username = "Johnny";

        Quest stubbedQuest = getStubbedQuest(questName);
        Solution usersSolution = getUsersSolution(username);

        when(questRepository.getQuest(1l)).thenReturn(stubbedQuest);
        when(dockerClient.checkSolution(questName, username)).thenReturn("tests with word SUCCESS");
        when(gitClient.checkIfGithubBranchExists(questName, username)).thenReturn(true);

        var solution = solutionManager.addSolution(usersSolution);

        assertTrue(solution.isResult());
    }

    @Test
    void givenQuestWithId1LExists_and_solutionIsIncorrect_andBranchExists_returnSolutionWithResultFalse() throws IOException, DockerException, DockerCertificateException, InterruptedException {

        String questName = "hello-world";
        String username = "Johnny";

        Quest stubbedQuest = getStubbedQuest(questName);

        Solution usersSolution = getUsersSolution(username);

        when(questRepository.getQuest(1l)).thenReturn(stubbedQuest);
        when(dockerClient.checkSolution(questName, username)).thenReturn("tests with word FAILURE");
        when(gitClient.checkIfGithubBranchExists(questName, username)).thenReturn(true);

        var solution = solutionManager.addSolution(usersSolution);

        assertFalse(solution.isResult());
    }

    @Test
    void givenQuestWithId1LExists_and_solutionIsCorrect_andBranchDoesNotExist_returnSolutionWithResultTrue() throws IOException, DockerException, DockerCertificateException, InterruptedException {

        String questName = "hello-world";
        String username = "Johnny";

        Quest stubbedQuest = getStubbedQuest(questName);

        Solution usersSolution = getUsersSolution(username);

        when(questRepository.getQuest(1l)).thenReturn(stubbedQuest);
        when(dockerClient.checkSolution(questName, username)).thenReturn("tests with word SUCCESS");
        when(gitClient.checkIfGithubBranchExists(questName, username)).thenReturn(false);

        var solution = solutionManager.addSolution(usersSolution);

        assertTrue(solution.isResult());
    }

    @Test
    void givenQuestWithId1LExists_and_solutionIsIncorrect_andBranchDoesNotExist_returnSolutionWithResultFalse() throws IOException, DockerException, DockerCertificateException, InterruptedException {

        String questName = "hello-world";
        String username = "Johnny";

        Quest stubbedQuest = getStubbedQuest(questName);

        Solution usersSolution = getUsersSolution(username);

        when(questRepository.getQuest(1l)).thenReturn(stubbedQuest);
        when(dockerClient.checkSolution(questName, username)).thenReturn("tests with word FAILURE");
        when(gitClient.checkIfGithubBranchExists(questName, username)).thenReturn(false);

        var solution = solutionManager.addSolution(usersSolution);

        assertFalse(solution.isResult());
    }

}







