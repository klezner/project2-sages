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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GHRepository createRepository(String repoName,
                                         String fileContent,
                                         String commitMessage,
                                         String filePath) throws IOException {
        var ghRepository = github.createRepository(repoName)
                .private_(true)
                .create();
        
        ghRepository.createContent()
                .content(fileContent)
                .message(commitMessage)
                .path(filePath)
                .commit();
        return ghRepository;
    }
    
    public GHRepository getRepository(String repoName) throws IOException {
        return github.getRepository(repoName);
    }

    public void createBranchOnRepository(GHRepository repository, String branchName) throws IOException {
        String sha1 = repository.getBranch("main").getSHA1();
        repository.createRef("refs/heads/" + branchName, sha1);
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
