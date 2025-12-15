package org.example.LMSProject.service;

import lombok.RequiredArgsConstructor;
import org.example.LMSProject.dao.GroupsRepository;
import org.example.LMSProject.dto.GroupInDto;
import org.example.LMSProject.dto.GroupOutDto;
import org.example.LMSProject.mapper.GroupMapper;
import org.example.LMSProject.model.Group;
import org.hibernate.Hibernate;
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

    public List<GroupOutDto> getAllGroups(){
        return groupMapper.toDtoList(groupsRepository.findAll());
    }

    public GroupOutDto getGroupById(Long id) {
        Optional<Group> foundGroup = groupsRepository.findById(id);
        foundGroup.ifPresent(group ->
                Hibernate.initialize(group.getStudents()));
        return groupMapper.toDto(foundGroup.orElse(null));
    }

    public Group findGroupById(Long id) {
        Optional<Group> foundGroup = groupsRepository.findById(id);
        return foundGroup.orElse(null);
    }

    @Transactional
    public void save(GroupInDto groupInDto){
        groupsRepository.save(groupMapper.toEntity(groupInDto));
    }

    @Transactional
    public void update(GroupInDto groupInDto){
        Group foundGroup = groupsRepository.findById(groupInDto.getId()).orElse(null);
        if(foundGroup != null){
            foundGroup.setName(groupInDto.getName());
            groupsRepository.save(foundGroup);
        }
    }

    @Transactional
    public void addStudents(GroupInDto groupInDto){
        groupsRepository.findById(groupInDto.getId()).ifPresent(
                foundGroup -> {
                    for(Long studentId : groupInDto.getStudentIds()) {
                        studentsService.setGroup(studentId, foundGroup);
                    }
                }
        );
    }

    @Transactional
    public void delete(GroupInDto groupInDto){
        groupsRepository.deleteById(groupInDto.getId());
    }
}
