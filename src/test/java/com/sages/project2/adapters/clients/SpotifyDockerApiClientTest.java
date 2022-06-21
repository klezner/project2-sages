package com.sages.project2.adapters.clients;

import com.sages.project2.domain.ports.out.DockerApiClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class SpotifyDockerApiClientTest {

    public static final String BRANCH_NAME = "username-123";
    @Autowired
    private DockerApiClient dockerApiClient;
    @Autowired
    private GithubApiClient githubApiClient;

    @Test
    void contextLoads() {
    }

    @BeforeAll
    public void beforeAll() {
        githubApiClient.connect();
    }

    @Test
    void givenRepoAndBranchBothExistAndSolutionOnMainIsInCorrect_whenCheckSolution_thenReturnStringContainingFailure() throws DockerException, DockerCertificateException, InterruptedException {
        var res = dockerApiClient.checkSolution("hello-world", "main");
        assertTrue(res.contains("FAILURE"));
    }

    @Test
    void givenRepoAndBranchBothExistAndSolutionOnUsersBranchIsCorrect_whenCheckSolution_thenReturnStringContainingSuccess() throws DockerException, DockerCertificateException, InterruptedException, IOException {
        var res = dockerApiClient.checkSolution("hello-world", BRANCH_NAME);
        assertTrue(res.contains("SUCCESS"));
    }
}

