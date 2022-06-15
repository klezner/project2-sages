package com.sages.project2.domain.ports.out;

import java.io.IOException;

public interface GitClient {

    String createRepository(String repoName) throws IOException;

    void changeFileContentOnBranch(String repoName, String branchName, String content, String commitMessage) throws IOException;

}
