package org.example.lmsproject.job;

import lombok.RequiredArgsConstructor;
import org.example.lmsproject.dao.SchedulesRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ScheduleDeleteJob {
    private final SchedulesRepository schedulesRepository;

    @Scheduled(cron = "${app.scheduler.delete-old-schedules.cron}")
    @Transactional
    public void deleteYearOldSchedules() {
        schedulesRepository.deleteByLessonDateBefore(LocalDateTime.now().minusYears(1));
    }
}
