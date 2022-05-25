package com.sages.project2.adapter.persistence.mapper;

import com.sages.project2.adapter.persistence.entity.UserEntity;
import com.sages.project2.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {

    UserEntity toEntity(User user);
}
