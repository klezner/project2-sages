package com.sages.project2.adapters.clients;

import com.sages.project2.commons.FileManager;
import com.sages.project2.domain.exceptions.RepositoryAlreadyExistsException;
import com.sages.project2.domain.exceptions.RepositoryDoesNotExistException;
import com.sages.project2.domain.ports.out.GitClient;
import lombok.RequiredArgsConstructor;
import org.kohsuke.github.*;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class GithubApiClient implements GitClient {

    // admin username może być do wyjęcia z security context
    private static final String ADMIN_GH_LOGIN = "bartmj";
    // klasa, do której trafiają rozwiązania użytkownika
    public static final String PATH_TO_MAIN_CLASS = "src/main/java/Main.java";
    public static final String DELETE_TOKEN = "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";

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

    public void createRepoContent(String repoName, String fileContent, String commitMessage, String filePath) throws IOException {
        var repository = getRepository(repoName);
        if (repository.isPresent()) {
            repository.get()
                    .createContent()
                    .content(fileContent)
                    .message(commitMessage)
                    .path(filePath)
                    .commit();
        } else {
            throw new RepositoryDoesNotExistException();
        }

    }

    public Optional<GHBranch> getGithubBranch(String repoName, String branchName) throws IOException {
        try {
            return Optional.of(github.getRepository(ADMIN_GH_LOGIN + "/" + repoName).getBranch(branchName));
        } catch (FileNotFoundException e) {
            return Optional.empty();
        }
    }

    public Optional<GHRepository> getRepository(String repoName) throws IOException {
        try {
            return Optional.of(github.getRepository(ADMIN_GH_LOGIN + "/" + repoName));
        } catch (FileNotFoundException e) {
            return Optional.empty();
        }
    }

    public void createBranchOnRepository(String repoName, String branchName) throws IOException {
        var repository = getRepository(repoName).get();
        String sha1 = repository.getBranch("main").getSHA1();
        repository.createRef("refs/heads/" + branchName, sha1);
    }

    @Override
    public Optional<GHBranch> checkIfGithubBranchExists(String repoName, String branchName) throws IOException {
        return Optional.empty();
    }

    public void changeFileContentOnBranch(String repoName, String branchName,
                                          String content, String commitMessage)
            throws IOException {
        var repository = getRepository(repoName);
        if (repository.isPresent()) {
            createBranchIfNotExist(repoName, branchName, content, commitMessage, repository);
        } else {
            throw new RepositoryDoesNotExistException();
        }
    }

    private void createBranchIfNotExist(String repoName, String branchName, String content, String commitMessage, Optional<GHRepository> repository) throws IOException {
        var branch = getGithubBranch(repoName, branchName);
        if (branch.isPresent()) {
            repository.get().getFileContent(PATH_TO_MAIN_CLASS, branchName).update(content, commitMessage, branchName);
        } else {
            createBranchOnRepository(repoName, branchName);
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

    public String deleteRepo(String helloWorld) throws IOException {
        URL url = new URL("https://api.github.com/repos/" + ADMIN_GH_LOGIN + "/" + helloWorld);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
        connection.setRequestProperty("Authorization", "Bearer " + DELETE_TOKEN);
        connection.setRequestMethod("DELETE");

        var responseMessage = connection.getResponseMessage();
        connection.disconnect();
        System.out.println("Repo deleted");
        return responseMessage;
    }
}
