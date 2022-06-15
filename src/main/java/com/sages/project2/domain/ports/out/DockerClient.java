package com.sages.project2.domain.ports.out;

public interface DockerClient {

    String checkSolution(String repoName, String branchName);

}
