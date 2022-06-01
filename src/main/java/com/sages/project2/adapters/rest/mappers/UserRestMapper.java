package com.sages.project2.adapters.rest.mappers;

import com.sages.project2.adapters.rest.dtos.UserDto;
import com.sages.project2.domain.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRestMapper {

    User toDomain(UserDto userDto);
}
