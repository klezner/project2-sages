package com.sages.project2.adapters.clients;

import org.junit.jupiter.api.*;
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

    public static final String HELLO_WORLD = "hello-world-123456789";

    @Autowired
    private GithubApiClient githubApiClient;

    @Test
    void contextLoads() {
    }

    @BeforeAll
    void beforeAll() throws IOException {
        githubApiClient.connect();
        githubApiClient.deleteRepo(HELLO_WORLD);
        githubApiClient.createRepository(HELLO_WORLD);
        githubApiClient.createRepoContent(HELLO_WORLD, "Readme file content",
                "This commit is adding README file",
                "README.md");
    }

    @AfterAll
    void afterAll() throws IOException {
        githubApiClient.deleteRepo(HELLO_WORLD);
    }

    @Test
    void GivenHelloWorldRepoExistsOnGh_getRepository_shouldReturnPresentOptionalOfGhRepo() throws IOException {
        var repository = githubApiClient.getRepository(HELLO_WORLD);
        assertInstanceOf(GHRepository.class, repository.get());
    }

    @Test
    void GivenRepoDoesNotExistOnGh_getRepository_shouldReturnEmptyOptional() throws IOException {
        var repository = githubApiClient.getRepository("!@#$%^&*");
        assertTrue(repository.isEmpty());
    }

    @Test
    void givenHelloWorldRepoExistsOnGhAndBranchExists_getGithubBranch_shouldReturnPresentOptional() throws IOException {
        var branch = githubApiClient.getGithubBranch(HELLO_WORLD, "main");
        assertInstanceOf(GHBranch.class, branch.get());
    }

    @Test
    void givenHelloWorldRepoExistsOnGhAndBranchDoesNot_getGithubBranch_shouldReturnEmptyOptional() throws IOException {
        var branch = githubApiClient.getGithubBranch(HELLO_WORLD, "!@#$%^&*");
        assertTrue(branch.isEmpty());
    }

}