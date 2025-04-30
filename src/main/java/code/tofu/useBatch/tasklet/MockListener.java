package code.tofu.useBatch.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

@Slf4j
public class MockListener implements StepExecutionListener {
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        Long taskletParam = Long.parseLong(stepExecution.getJobParameters().getString("taskletParam"));
        if(taskletParam % 2 == 0){
            log.info("[MockListener] After Step is Even");
            return new ExitStatus("EVEN");
        } else {
            log.info("[MockListener] After Step is Odd");
            return new ExitStatus("ODD");
        }
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("[MockListener] MockListener is listening before step");
        StepExecutionListener.super.beforeStep(stepExecution);
    }
}
