package com.sages.project2;

import com.sages.project2.adapters.clients.GithubApiClient;

import static java.lang.String.format;

import org.kohsuke.github.GHRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class Application {

    public static final String REPO_NAME = "hello-world-1";
    public static final String NEW_BRANCH = "new-branch";
    public static final String ADMIN_GITHUB_NAME = "bartmj";
    private static final String PATH_TO_MAIN = "src/main/java/Main.java";
    private static final String USERS_SOLUTION = "public class Main {\n" +
            "    public static void main(String[] args) {\n" +
            "        System.out.println(\"Hello World!\");\n" +
            "    }\n" +
            "}";

    public static void main(String[] args) throws IOException {

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        GithubApiClient githubAPICLient = context.getBean("githubApiClient", GithubApiClient.class);

        githubAPICLient.connect();
        System.out.println("Github client connected");

//        githubAPICLient.createRepository(REPO_NAME);
//        System.out.println("New repository created");

        var fullRepoName = format("%s/%s", ADMIN_GITHUB_NAME, REPO_NAME);

        var repository = githubAPICLient.getRepository(fullRepoName);

//        githubAPICLient.createBranchOnRepository(repository, NEW_BRANCH);
//        System.out.println("New branch created");

        githubAPICLient.changeFileContentOnBranch(repository, NEW_BRANCH, PATH_TO_MAIN, USERS_SOLUTION, "commit message");


//        githubAPICLient.createBranchOnRepository(repository, NEW_BRANCH);
//        System.out.println("New branch created");
//
//        githubAPICLient.addFileToBranch(new File("src/main/java/com/sages/project2"),
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


