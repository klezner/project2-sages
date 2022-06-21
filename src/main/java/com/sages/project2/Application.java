package com.sages.project2;

import com.sages.project2.adapters.clients.GithubApiClient;
import com.sages.project2.domain.ports.in.QuestService;
import com.sages.project2.domain.ports.in.SolutionService;
import com.sages.project2.domain.ports.out.*;
import com.sages.project2.domain.services.QuestManager;
import com.sages.project2.domain.services.SolutionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);


        GithubApiClient githubAPICLient = context.getBean("githubApiClient", GithubApiClient.class);

        githubAPICLient.connect();
        System.out.println("Github client connected");
    }
}


