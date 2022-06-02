package com.sages.project2.adapters.clients;

import com.sages.project2.commons.FileManager;
import lombok.RequiredArgsConstructor;
import org.kohsuke.github.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class GithubApiClient {

    private GitHub github;

    public void connect() {
        try {
            this.github = GitHubBuilder.fromPropertyFile("src/main/resources/properties.github").build();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String createRepository(String repoName) throws IOException {
        GHRepository repository = github.createRepository(repoName).create();

        repository.createContent()
                .content("Readme file for " + repository.getFullName())
                .message("This commit is adding README file")
                .path("README.md")
                .commit();
        return repository.getFullName();
    }

    public GHRepository getRepository(String repoName) {
        GHRepository repository = null;
        try {
            repository = github.getRepository(repoName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return repository;
    }

    public GHBranch createBranchOnRepository(String repoName, String branchName) throws IOException {
        GHRepository repository = getRepository(repoName);
        String sha1 = repository.getBranch("main").getSHA1();
        repository.createRef("refs/heads/" + branchName, sha1);
        return repository.getBranch(branchName);
    }

    public void addFileToBranch(File file, String repoName, String branchName) throws IOException {
        GHRepository repository = getRepository(repoName);
        repository.createContent()
                .branch(branchName)
                .content(FileManager.readfileAsBytes(file))
                .message("New java file added to branch")
                .path(file.getName())
                .commit();
    }

}
