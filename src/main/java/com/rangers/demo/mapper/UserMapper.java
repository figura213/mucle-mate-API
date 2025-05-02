package com.rangers.demo.mapper;

import com.rangers.demo.dto.UserDto;
import com.rangers.demo.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "userId", source = "userId", qualifiedByName = "uuidToString")
    @Mapping(target = "generalInformation.userName", source = "userName")
    @Mapping(target = "generalInformation.email", source = "email")
    @Mapping(target = "generalInformation.gender", source = "gender")
    @Mapping(target = "generalInformation.goal", source = "goal")
    @Mapping(target = "metrics.age", source = "age")
    @Mapping(target = "metrics.height", source = "height")
    @Mapping(target = "metrics.weight", source = "weight")
    @Mapping(target = "metrics.physicalActivityLevel", source = "physicalActivityLevel")
    UserDto toDto(User user);

    @Mapping(target = "userId", source = "userId", qualifiedByName = "stringToUuid")
    @Mapping(target = "userName", source = "generalInformation.userName")
    @Mapping(target = "email", source = "generalInformation.email")
    @Mapping(target = "gender", source = "generalInformation.gender")
    @Mapping(target = "goal", source = "generalInformation.goal")
    @Mapping(target = "age", source = "metrics.age")
    @Mapping(target = "height", source = "metrics.height")
    @Mapping(target = "weight", source = "metrics.weight")
    @Mapping(target = "physicalActivityLevel", source = "metrics.physicalActivityLevel")
    User toEntity(UserDto userDto);

    @Named("uuidToString")
    default String uuidToString(java.util.UUID uuid) {
        return uuid != null ? uuid.toString() : null;
    }

    @Named("stringToUuid")
    default java.util.UUID stringToUuid(String uuid) {
        return uuid != null ? java.util.UUID.fromString(uuid) : null;
    }
}
