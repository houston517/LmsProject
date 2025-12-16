package org.example.lmsproject.dao;

import org.example.lmsproject.model.Schedule;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SchedulesRepository extends JpaRepository<Schedule, Long> {
    @Query("select s from Schedule s where (s.group.id = :groupId OR :groupId is null) AND (s.teacher.id = :teacherId OR :teacherId is null)")
    List<Schedule> getSchedulesByQuery(Long groupId, Long teacherId, Pageable pageable);
}
