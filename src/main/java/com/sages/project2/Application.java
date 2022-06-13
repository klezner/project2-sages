package com.sages.project2;

import com.sages.project2.adapters.clients.GithubApiClient;

import static java.lang.String.format;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        GithubApiClient githubAPICLient = context.getBean("githubApiClient", GithubApiClient.class);

        githubAPICLient.connect();
        System.out.println("Github client connected");

    }
}


