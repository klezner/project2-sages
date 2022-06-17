package com.sages.project2.adapters.clients;

import com.sages.project2.commons.FileManager;
import com.sages.project2.domain.exceptions.BranchDoesNotExistException;
import com.sages.project2.domain.exceptions.RepositoryAlreadyExistsException;
import com.sages.project2.domain.exceptions.RepositoryDoesNotExistException;
import com.sages.project2.domain.ports.out.GitClient;
import lombok.RequiredArgsConstructor;
import org.kohsuke.github.*;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class GithubApiClient implements GitClient {

    // admin username może być do wyjęcia z security context
    private static final String ADMIN_GH_LOGIN = "bartmj";
    // klasa, do której trafiają rozwiązania użytkownika
    public static final String PATH_TO_MAIN_CLASS = "src/main/java/Main.java";

    private GitHub github;

    public void connect() {
        try {
            this.github = GitHubBuilder.fromPropertyFile("src/main/resources/properties.github").build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createRepository(String repoName) throws IOException {
        var repository = getRepository(repoName);
        if (repository.isPresent()) {
            throw new RepositoryAlreadyExistsException();
        }
        var repo = github.createRepository(repoName)
                .private_(true)
                .create();
        return repo.getHtmlUrl().toString();
    }

    public GHBranch checkIfGithubBranchExists(String repoName, String branchName) throws IOException {
        return Optional.of(github.getRepository(ADMIN_GH_LOGIN + "/" + repoName).getBranch(branchName))
                .orElseThrow(BranchDoesNotExistException::new);
    }

    public Optional<GHRepository> getRepository(String repoName) throws IOException {
        GHRepository ghRepository;
        try {
            ghRepository = github.getRepository(ADMIN_GH_LOGIN + "/" + repoName);
            return Optional.of(ghRepository);
        } catch (FileNotFoundException e) {
            return Optional.empty();
        }
    }

    public void createBranchOnRepository(String repoName, String branchName) throws IOException {
        var repository = getRepository(repoName).get();
        String sha1 = repository.getBranch("main").getSHA1();
        repository.createRef("refs/heads/" + branchName, sha1);
    }

    public void changeFileContentOnBranch(String repoName, String branchName,
                                          String content, String commitMessage)
            throws IOException {
        var repository = getRepository(repoName);
        if (repository.isPresent()) {
            repository.get().getFileContent(PATH_TO_MAIN_CLASS, branchName).update(content, commitMessage, branchName);
        } else {
            throw new RepositoryDoesNotExistException();
        }
    }

    public void addFileToBranch(File file,
                                GHRepository repository,
                                String branchName,
                                String message) throws IOException {
        repository.createContent()
                .branch(branchName)
                .content(FileManager.readfileAsBytes(file))
                .message(message)
                .path(file.getName())
                .commit();
    }
}
