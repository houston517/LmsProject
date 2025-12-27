package org.example.lmsproject.service;

import lombok.RequiredArgsConstructor;
import org.example.lmsproject.dao.GroupsRepository;
import org.example.lmsproject.dto.GroupInDto;
import org.example.lmsproject.dto.GroupOutDto;
import org.example.lmsproject.exception.GroupNotFoundException;
import org.example.lmsproject.mapper.GroupMapper;
import org.example.lmsproject.model.Group;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class GroupsService {
    private final StudentsService studentsService;
    private final GroupsRepository groupsRepository;
    private final GroupMapper groupMapper;

    public List<GroupOutDto> getAll(Pageable pageable) {
        return groupMapper.toDtoList(groupsRepository.findAll(pageable).toList());
    }

    public GroupOutDto getGroupById(Long id) {
        Optional<Group> foundGroup = groupsRepository.findById(id);
        foundGroup.ifPresent(group ->
                Hibernate.initialize(group.getStudents()));
        return groupMapper.toDto(foundGroup.orElseThrow(() -> new GroupNotFoundException(id.toString())));
    }

    public Group findGroupById(Long id) {
        Optional<Group> foundGroup = groupsRepository.findById(id);
        return foundGroup.orElseThrow(() -> new GroupNotFoundException(id.toString()));
    }

    @Transactional
    public void save(GroupInDto groupInDto){
        groupsRepository.save(groupMapper.toEntity(groupInDto));
    }

    @Transactional
    public void update(GroupInDto groupInDto){
        Group foundGroup = groupsRepository.findById(groupInDto.getId())
                .orElseThrow(() -> new GroupNotFoundException(groupInDto.getId().toString()));
        foundGroup.setName(groupInDto.getName());
        groupsRepository.save(foundGroup);
    }

    @Transactional
    public void addStudents(GroupInDto groupInDto){
        Group foundGroup = groupsRepository.findById(groupInDto.getId())
                .orElseThrow(() -> new GroupNotFoundException(groupInDto.getId().toString()));
        for(Long studentId : groupInDto.getStudentIds()) {
            studentsService.setGroup(studentId, foundGroup);
        }

    }

    @Transactional
    public void delete(GroupInDto groupInDto){
        groupsRepository.deleteById(groupInDto.getId());
    }
}
