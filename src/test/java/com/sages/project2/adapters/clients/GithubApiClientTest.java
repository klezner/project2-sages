package com.sages.project2.adapters.clients;

import com.sages.project2.domain.exceptions.BranchAlreadyExistsException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kohsuke.github.*;
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
        githubApiClient.createRepoContent(HELLO_WORLD, "public class Main {\n" +
                        "    public static void main(String[] args) { }\n" +
                        "}",
                "This commit message",
                "src/main/java/Main.java");
    }

//    @AfterAll
//    void afterAll() throws IOException {
//        githubApiClient.deleteRepo(HELLO_WORLD);
//        System.out.println("After tests");
//    }

    @Test
    void givenHelloWorldRepoExistsOnGh_getRepository_thenReturnPresentOptionalOfGhRepo() throws IOException {
        var repository = githubApiClient.getRepository(HELLO_WORLD);
        assertInstanceOf(GHRepository.class, repository.get());
    }

    @Test
    void givenRepoDoesNotExistOnGh_getRepository_returnEmptyOptional() throws IOException {
        var repository = githubApiClient.getRepository("!@#$%^&*");
        assertTrue(repository.isEmpty());
    }

    @Test
    void givenHelloWorldRepoExistsOnGhAndBranchExists_getGithubBranch_returnPresentOptional() throws IOException {
        var branch = githubApiClient.getGithubBranch(HELLO_WORLD, "main");
        assertTrue(branch.isPresent());
    }

    @Test
    void givenHelloWorldRepoExistsOnGhAndBranchDoesNot_whenGetGithubBranch_returnEmptyOptional() throws IOException {
        var branch = githubApiClient.getGithubBranch(HELLO_WORLD, "!@#$%^&*");
        assertTrue(branch.isEmpty());
    }

    @Test
    void givenBranchDoesNotExist_whenCreateBranchOnRepository_thenBranchIsCreated() throws IOException {
        githubApiClient.createBranchOnRepository(HELLO_WORLD, "username");
        assertTrue(githubApiClient.checkIfGithubBranchExists(HELLO_WORLD, "username"));
    }

    @Test
    void givenBranchExists_checkIfGithubBranchExists_returnTrue() throws IOException {
        assertEquals(githubApiClient.checkIfGithubBranchExists(HELLO_WORLD, "main"), true);
    }

    @Test
    void givenBranchDoesNotExist_checkIfGithubBranchExists_returnFalse() throws IOException {
        assertEquals(githubApiClient.checkIfGithubBranchExists(HELLO_WORLD, "!@#$%^&*"), false);
    }

    @Test
    void givenBranchExists_whenCreateBranchOnRepository_throwsBranchAlreadyExistsException() {
        assertThrows(BranchAlreadyExistsException.class, () -> githubApiClient.createBranchOnRepository(HELLO_WORLD, "main"));
    }

    @Test
    void givenRepoIsNotEmpty_changeFileContentOnBranch_returnOptionalOfGhContent() throws IOException {
        githubApiClient.changeFileContentOnBranch(HELLO_WORLD, "username", "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.print(\"Hello World!\");\n" +
                "    }\n" +
                "}", "Commit message");
        var content = githubApiClient.getFileContentOnBranch(HELLO_WORLD, "username");
        assertTrue(content.isPresent());
    }

}