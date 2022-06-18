package com.sages.project2.domain.ports.out;

import org.kohsuke.github.GHBranch;

import java.io.IOException;
import java.util.Optional;

public interface GitClient {

    public void createBranchOnRepository(String repoName, String branchName) throws IOException;

    public boolean checkIfGithubBranchExists(String repoName, String branchName) throws IOException;

    String createRepository(String repoName) throws IOException;

    void changeFileContentOnBranch(String repoName, String branchName, String content, String commitMessage) throws IOException;

}
