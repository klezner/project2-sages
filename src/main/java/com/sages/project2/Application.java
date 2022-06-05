package com.sages.project2;

import com.sages.project2.adapters.clients.GithubApiClient;
import org.kohsuke.github.GHContentBuilder;
import org.kohsuke.github.GHContentUpdateResponse;
import org.kohsuke.github.GHRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class Application {

    public static final String REPO_NAME = "hello-world-final";
    public static final String NEW_BRANCH = "new-branch";

    public static void main(String[] args) throws IOException {

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        GithubApiClient githubAPICLient = context.getBean("githubApiClient", GithubApiClient.class);

        githubAPICLient.connect();
        System.out.println("Github client connected");
//
//        GHRepository repository = githubAPICLient.createRepository(REPO_NAME,
//                "Readme file content",
//                "This commit is adding README file",
//                "README.md");
//        System.out.println("New repository committed with README.md");
//
//        githubAPICLient.createBranchOnRepository(repository, NEW_BRANCH);
//        System.out.println("New branch created");
//
//        githubAPICLient.addFileToBranch(new File("src/main/java/com/sages/project2/WebSecurityConfig.java"),
//                repository,
//                NEW_BRANCH,
//                "New java file added to branch");
//
//        System.out.println("File added to the branch");
//
//        var repoFromGitHub = githubAPICLient.getRepository(repository.getFullName());
//        System.out.println("Repo from GitHub: " + repoFromGitHub.getFullName());

    }

}


