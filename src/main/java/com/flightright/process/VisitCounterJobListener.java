package com.flightright.process;


import com.flightright.entity.VisitCount;
import com.flightright.service.VisitService;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class VisitCounterJobListener implements JobExecutionListener {
    private final VisitService visitService;
    private VisitsCounterWriter visitsCounterWriter;

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("JobExecutionListener - afterStep");
        if (jobExecution.getStatus().equals(BatchStatus.COMPLETED)) {
            Optional<VisitCount> visit = visitService.findFirstVisitCount();
            if (visit.isEmpty()) {
                visit = Optional.of(new VisitCount());
            }
            visit.get().setCount(visitsCounterWriter.getUserVisitsCount());
            visitService.save(visit.get());
            visitsCounterWriter.clearUserVisitSet();
        }
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
    }
}
