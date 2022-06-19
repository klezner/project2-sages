package com.sages.project2.adapters.rest.mappers;

import com.sages.project2.adapters.persistence.entities.UserEntity;
import com.sages.project2.domain.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRestMapper {

    UserEntity toEntity (User user);

    User toDomain (UserEntity userEntity);
}
