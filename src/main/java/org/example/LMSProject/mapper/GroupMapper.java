package org.example.LMSProject.mapper;

import org.example.LMSProject.dto.GroupInDto;
import org.example.LMSProject.dto.GroupOutDto;
import org.example.LMSProject.model.Group;
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
