package com.sages.project2.domain.ports.out;

import com.sages.project2.domain.models.Quest;
import com.sages.project2.domain.models.Solution;

import java.io.IOException;

public interface SolutionRepository {

    Solution saveSolution(Solution solution) throws IOException;

}
