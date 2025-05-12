package code.tofu.useBatch.tasklet;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class TaskletFlowConfig {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    PlatformTransactionManager transactionManager;

    Boolean failFlag = false;

    @Bean
    public Flow getTaskletFlow(){
        log.info("[TaskletFlow] start externalTaskletFlow");
        return new FlowBuilder<SimpleFlow>("taskletFlow")
                .start(failableTasklet())
                //handle failed scenario
                .on("COMPLETED")
                    .to(getExceptionDecider())
                .from(failableTasklet()).on("FAILED")
                    .to(backupTasklet())
                //implement custom decider
                .from(failableTasklet()).on("EXCEPTIONED")
                    .to(exceptionedTasklet())
                .build();
    }

    @Bean
    public Step failableTasklet(){
        return new StepBuilder("failableTasklet",jobRepository).tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                String jobParam = chunkContext.getStepContext().getJobParameters().get("taskletParam").toString();
                if(failFlag) {
                    log.info("[TaskletFlow] failableTasklet Failed");
                    throw new Exception("Forced Failure");
                }
                if(Integer.parseInt(jobParam) % 2 == 0) {
                    log.info("[TaskletFlow] failableTasklet Failed");
                    throw new Exception("Param Failure");
                }
                log.info("[TaskletFlow] Execute failableTasklet with taskletParam:{}",jobParam);
                return RepeatStatus.FINISHED;
            }
        },transactionManager).build();
    }

    @Bean
    public Step backupTasklet(){
        return new StepBuilder("backupTasklet",jobRepository).tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                String jobParam = chunkContext.getStepContext().getJobParameters().get("taskletParam").toString();
                log.info("[TaskletFlow]Execute backupTasklet with taskletParam:{}",jobParam);
                return RepeatStatus.FINISHED;
            }
        },transactionManager).build();
    }

    @Bean
    public Step exceptionedTasklet(){
        return new StepBuilder("exceptionedTasklet",jobRepository).tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                String jobParam = chunkContext.getStepContext().getJobParameters().get("taskletParam").toString();
                log.info("[TaskletFlow] Execute exceptionedTasklet with taskletParam:{}",jobParam);
                return RepeatStatus.FINISHED;
            }
        },transactionManager).build();
    }


    @Bean
    public JobExecutionDecider getExceptionDecider(){
        return new ExceptionDecider();
    }




}
