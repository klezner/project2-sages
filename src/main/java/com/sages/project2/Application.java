package com.sages.project2;

import com.sages.project2.adapters.clients.GithubApiClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        GithubApiClient githubAPICLient = context.getBean("githubApiClient", GithubApiClient.class);

        githubAPICLient.connect();
        System.out.println("Github client connected");
        String repositoryName = githubAPICLient.createRepository("username/repo");
        System.out.println("New repository created");
        githubAPICLient.createBranchOnRepository(repositoryName, "new-branch");
        System.out.println("New branch created");

        githubAPICLient.addFileToBranch(new File("src/main/java/com/sages/project2/WebSecurityConfig.java"),
                repositoryName,
                "new-branch");
        System.out.println("File added to branch");
    }

}
