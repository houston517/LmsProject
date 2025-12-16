package org.example.lmsproject.mapper;

import org.example.lmsproject.dto.GroupInDto;
import org.example.lmsproject.dto.GroupOutDto;
import org.example.lmsproject.model.Group;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GroupMapper {
    GroupOutDto toDto(Group group);
    Group toEntity(GroupInDto groupInDto);
    List<GroupOutDto> toDtoList(List<Group> groups);

    default String map(Group group){
        return null;
    }
}
