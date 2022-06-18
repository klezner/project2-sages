package com.sages.project2.adapters.clients;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class GithubApiClientTest {

    @Autowired
    private GithubApiClient githubApiClient;

    @Test
    void contextLoads() {
    }

    @BeforeAll
    void GivenRightUserCredentials_ShouldConnectToGhApiClient() {
        githubApiClient.connect();
    }

    @Test
    void GivenHelloWorldRepoExistsOnGh_getRepository_shouldReturnPresentOptionalOfGhRepo() throws IOException {
        var repository = githubApiClient.getRepository("hello-world");
        assertInstanceOf(GHRepository.class, repository.get());
    }

    @Test
    void GivenRepoDoesNotExistOnGh_getRepository_shouldReturnEmptyOptional() throws IOException {
        var repository = githubApiClient.getRepository("!@#$%^&*");
        assertTrue(repository.isEmpty());
    }

    @Test
    void givenHelloWorldRepoExistsOnGhAndBranchExists_getGithubBranch_shouldReturnPresentOptional() throws IOException {
        var branch = githubApiClient.getGithubBranch("hello-world", "main");
        assertInstanceOf(GHBranch.class, branch.get());
    }

    @Test
    void givenHelloWorldRepoExistsOnGhAndBranchDoesNot_getGithubBranch_shouldReturnEmptyOptional() throws IOException {
        var branch = githubApiClient.getGithubBranch("hello-world", "!@#$%^&*");
        assertTrue(branch.isEmpty());
    }

}