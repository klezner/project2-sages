package com.sages.project2.config;

import com.sages.project2.domain.ports.in.QuestService;
import com.sages.project2.domain.ports.in.SolutionService;
import com.sages.project2.domain.ports.out.*;
import com.sages.project2.domain.services.QuestManager;
import com.sages.project2.domain.services.SolutionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean(name = "questService")
    public QuestService getQuestService(QuestRepository questPersistenceAdapter,
                                        GitClient gitClient, UserRepository userRepository){
        return new QuestManager(questPersistenceAdapter, userRepository, gitClient);
    }

    @Bean(name = "solutionService")
    public SolutionService getSolutionService(QuestRepository questPersistenceAdapter,
                                           GitClient gitClient, DockerApiClient dockerApiClient,
                                           SolutionRepository solutionRepository){
        return new SolutionManager(questPersistenceAdapter, gitClient, dockerApiClient, solutionRepository);
    }

}
