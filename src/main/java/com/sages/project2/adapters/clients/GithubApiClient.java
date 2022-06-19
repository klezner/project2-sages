package com.sages.project2.adapters.clients;

import com.sages.project2.commons.FileManager;
import com.sages.project2.domain.exceptions.BranchAlreadyExistsException;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class GithubApiClient implements GitClient {

    private static final String ADMIN_GH_LOGIN = "codequest504";
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

    public boolean createBranchOnRepository(String repoName, String branchName) throws IOException {
        if (checkIfGithubBranchExists(repoName, branchName)) {
            throw new BranchAlreadyExistsException();
        }
        var repository = getRepository(repoName).get();
        String sha1 = repository.getBranch("main").getSHA1();
        repository.createRef("refs/heads/" + branchName, sha1);
        return true;
    }

    @Override
    public boolean checkIfGithubBranchExists(String repoName, String branchName) throws IOException {
        try {
            getRepository(repoName).get().getBranch(branchName);
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public Optional<GHContent> getFileContentOnBranch(String repoName, String branchName) throws IOException {
        try {
            return Optional.of(getRepository(repoName).get().getFileContent(PATH_TO_MAIN_CLASS, branchName));
        } catch (FileNotFoundException e) {
            return Optional.empty();
        }
    }

    public boolean changeFileContentOnBranch(String repoName, String branchName,
                                          String content, String commitMessage)
            throws IOException {
        var repository = getRepository(repoName);
        if (repository.isPresent()) {
            var branchExists = checkIfGithubBranchExists(repoName, branchName);
            if (branchExists) {
                repository.get().getFileContent(PATH_TO_MAIN_CLASS, branchName).update(content, commitMessage, branchName);
                return true;
            } else {
                throw new BranchDoesNotExistException();
            }
        } else {
            throw new RepositoryDoesNotExistException();
        }
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
