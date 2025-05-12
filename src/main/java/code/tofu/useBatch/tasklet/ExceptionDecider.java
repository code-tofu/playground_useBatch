package code.tofu.useBatch.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

import java.util.Date;

@Slf4j
public class ExceptionDecider implements JobExecutionDecider {


    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        log.info("[ExceptionDecider] Deciding...");
        if(new Date().getTime() % 2 == 0) {
            log.error("Exception in JobExecutionDecider");
            return new FlowExecutionStatus("EXCEPTIONED");
        }
        return FlowExecutionStatus.COMPLETED;
    }
}