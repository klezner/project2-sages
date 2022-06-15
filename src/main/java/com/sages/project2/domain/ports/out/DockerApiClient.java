package com.sages.project2.domain.ports.out;

import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;

public interface DockerApiClient {

    String checkSolution(String repoName, String branchName) throws DockerCertificateException, DockerException, InterruptedException;

}
