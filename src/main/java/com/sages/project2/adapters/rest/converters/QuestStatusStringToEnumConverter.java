package com.sages.project2.adapters.rest.converters;

import com.sages.project2.domain.QuestDifficulty;
import org.springframework.core.convert.converter.Converter;

public class QuestStatusStringToEnumConverter implements Converter<String, QuestDifficulty> {

    @Override
    public QuestDifficulty convert(String source) {
        return QuestDifficulty.valueOf(source.toUpperCase());
    }

}
