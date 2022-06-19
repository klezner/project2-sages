package com.sages.project2.adapters.clients;

import com.sages.project2.domain.ports.out.DockerApiClient;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class SpotifyDockerApiClientTest {

    public static final String USERNAME = "username-123";
    @Autowired
    private DockerApiClient dockerApiClient;
    @Autowired
    private GithubApiClient githubApiClient;

    @Test
    void contextLoads() {
    }

    @BeforeAll
    public void beforeAll() throws IOException {
        githubApiClient.connect();
    }

    @Test
    void givenRepoExistsAndSolutionOnMainIsInCorrect_whenCheckSolution_thenReturnStringContainingFailure() throws DockerException, DockerCertificateException, InterruptedException {
        var res = dockerApiClient.checkSolution("hello-world", "main");
        assertTrue(res.contains("FAILURE"));
    }

    @Test
    void givenRepoExistsAndSolutionOnUsersBranchIsCorrect_whenCheckSolution_thenReturnStringContainingSuccess() throws DockerException, DockerCertificateException, InterruptedException, IOException {
        githubApiClient.createBranchOnRepository("hello-world", USERNAME);
        githubApiClient.changeFileContentOnBranch("hello-world", USERNAME, "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.print(\"Hello World!\");\n" +
                "    }\n" +
                "}", "test-commit");
        var res = dockerApiClient.checkSolution("hello-world", USERNAME);
        assertTrue(res.contains("SUCCESS"));
    }
}

