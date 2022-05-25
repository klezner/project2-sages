package com.sages.project2.adapter.rest.mapper;

import com.sages.project2.adapter.rest.dto.UserDto;
import com.sages.project2.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRestMapper {

    User toDomain(UserDto userDto);
}
