package code.tofu.useBatch.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

import java.util.Date;

@Slf4j
public class ExampleDecider implements JobExecutionDecider {


    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        log.info("[ExampleDecider] Deciding...");
        if(new Date().getTime() % 2 == 0) return new FlowExecutionStatus("EXCEPTIONED");
        return FlowExecutionStatus.COMPLETED;
    }
}