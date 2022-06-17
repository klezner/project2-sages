package com.sages.project2.config;

import com.sages.project2.adapters.rest.converters.QuestDifficultyStringToEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new QuestDifficultyStringToEnumConverter());
    }

}
