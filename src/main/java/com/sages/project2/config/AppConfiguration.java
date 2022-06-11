package com.sages.project2.config;

import com.sages.project2.domain.ports.in.QuestService;
import com.sages.project2.domain.ports.in.UserService;
import com.sages.project2.domain.ports.out.GithubApiRepository;
import com.sages.project2.domain.ports.out.QuestRepository;
import com.sages.project2.domain.ports.out.UserRepository;
import com.sages.project2.domain.services.QuestManager;
import com.sages.project2.domain.services.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public QuestService getQuestService(QuestRepository questPersistenceAdapter,
                                        GithubApiRepository githubApiClient){
        return new QuestManager(questPersistenceAdapter,githubApiClient);
    }

    @Bean
    public UserService getUserService(UserRepository userPersistenceAdapter){
        return new UserServiceImpl(userPersistenceAdapter);
    }


}
