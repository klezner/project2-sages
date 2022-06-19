package com.sages.project2.config;

import com.sages.project2.domain.ports.in.QuestService;
import com.sages.project2.domain.ports.out.GitClient;
import com.sages.project2.domain.ports.out.QuestRepository;
import com.sages.project2.domain.services.QuestManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public QuestService getQuestService(QuestRepository questPersistenceAdapter,
                                        UserRepository userRepository,
                                        GitClient gitClient){
        return new QuestManager(questPersistenceAdapter,userRepository, gitClient);
                                        GitClient gitClient, DockerApiClient dockerClient,
                                        SolutionRepository solutionRepository) {
        return new QuestManager(questPersistenceAdapter, gitClient, dockerClient, solutionRepository);
    }

}
