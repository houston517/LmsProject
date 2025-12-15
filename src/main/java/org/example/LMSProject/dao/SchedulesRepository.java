package org.example.LMSProject.dao;

import org.example.LMSProject.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchedulesRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> getSchedulesByGroupId(Long groupId);
    List<Schedule> getSchedulesByTeacherId(Long teacherId);
    List<Schedule> getSchedulesByGroupIdAndTeacherId(Long groupId, Long teacherId);
}
