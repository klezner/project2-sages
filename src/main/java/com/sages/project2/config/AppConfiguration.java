package com.sages.project2.config;

import com.sages.project2.domain.ports.in.QuestService;
import com.sages.project2.domain.ports.in.QuestVerificationService;
import com.sages.project2.domain.ports.out.*;
import com.sages.project2.domain.services.QuestManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public QuestService getQuestService(QuestRepository questPersistenceAdapter,
                                        GitClient gitClient, DockerApiClient dockerClient,
                                        SolutionRepository solutionRepository, UserRepository userRepository,
                                        QuestVerificationService verificationService) {
        return new QuestManager(questPersistenceAdapter, userRepository, gitClient, dockerClient, solutionRepository, verificationService);
    }

}
