package com.sages.project2.domain.ports.out;

import java.io.IOException;

public interface GitClient {

    String createRepository(String repoName) throws IOException;
}
