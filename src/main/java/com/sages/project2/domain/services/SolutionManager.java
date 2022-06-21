package com.sages.project2.domain.services;

import com.sages.project2.commons.aop.Atomic;
import com.sages.project2.domain.models.Solution;
import com.sages.project2.domain.ports.in.SolutionService;
import com.sages.project2.domain.ports.out.*;
import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class SolutionManager implements SolutionService {

    private final GitClient gitClient;
    private final DockerApiClient dockerClient;
    private final SolutionRepository solutionRepository;

    @Atomic
    @Override
    public Solution addSolution(Solution solution) throws IOException {
        var username = solution.getUsername();
        var questName = solution.getQuestName();

        if (!gitClient.checkIfGithubBranchExists(questName, username)) {
            gitClient.createBranchOnRepository(questName, username);
        }

        gitClient.changeFileContentOnBranch(questName, username, solution.getSolution(), "Commited by: " + username);
        String textFromContainer = null;
        try {
            textFromContainer = dockerClient.checkSolution(questName, username);
        } catch (DockerCertificateException | InterruptedException | DockerException e) {
            e.printStackTrace();
        }

        var verifiedSolution = verifySolution(solution, textFromContainer);

        solutionRepository.saveSolution(verifiedSolution);
        return verifiedSolution;
    }

    private Solution verifySolution(Solution solution, String checkedSolution) {
        if (checkedSolution.contains("SUCCESS")) {
            solution.setResult(true);
        } else {
            solution.setResult(false);
        }
        return solution;
    }
}
