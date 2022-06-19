package com.sages.project2.domain.ports.in;

import com.sages.project2.domain.models.Solution;

import java.io.IOException;


public interface SolutionService {

    Solution addSolution(Solution solution) throws IOException;
}
