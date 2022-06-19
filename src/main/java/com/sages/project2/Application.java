package com.sages.project2;

import com.sages.project2.adapters.clients.GithubApiClient;

import static java.lang.String.format;

import com.sages.project2.domain.exceptions.BranchDoesNotExistException;
import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHFileNotFoundException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.Optional;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);


        GithubApiClient githubAPICLient = context.getBean("githubApiClient", GithubApiClient.class);

        githubAPICLient.connect();
        System.out.println("Github client connected");

    }
}


