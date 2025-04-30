package code.tofu.useBatch.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Date;

@Configuration
@Slf4j
public class TaskletJobConfig {

    Boolean failFlag = false;

    @Bean
    public Step repeatableTasklet(JobRepository jobRepository, PlatformTransactionManager transactionManager){

        //NOTE: changing job name or tasklet CLI params name/value allows job to re-run (job parameters different)
        return new StepBuilder("repeatableTasklet",jobRepository).tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                String jobParam = chunkContext.getStepContext().getJobParameters().get("id").toString();
                log.info("[TaskletJob] Execute repeatableTasklet with id:{}",jobParam);
                if(new Date().getTime() % 2 == 0) {
                    log.info("[TaskletJob] repeatableTasklet Continue");
                    return RepeatStatus.CONTINUABLE;
                }
                log.info("[TaskletJob] repeatableTasklet Finished");
                return RepeatStatus.FINISHED; //NOTE: will be repeated until finished.
            }
        },transactionManager).build();
    }

    @Bean
    public Step failableTasklet(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("failableTasklet",jobRepository).tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                String jobParam = chunkContext.getStepContext().getJobParameters().get("taskletParam").toString();
                if(failFlag) {
                    log.info("[TaskletJob] failableTasklet Failed");
                    throw new Exception("Forced Failure");
                }
                if(Integer.parseInt(jobParam) % 2 == 0) {
                    log.info("[TaskletJob] failableTasklet Failed");
                    throw new Exception("Param Failure");
                }
                log.info("[TaskletJob] Execute failableTasklet with taskletParam:{}",jobParam);
                return RepeatStatus.FINISHED;
            }
        },transactionManager).build();
    }

    @Bean
    public Step backupTasklet(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("backupTasklet",jobRepository).tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                String jobParam = chunkContext.getStepContext().getJobParameters().get("taskletParam").toString();
                log.info("[TaskletJob]Execute backupTasklet with taskletParam:{}",jobParam);
                return RepeatStatus.FINISHED;
            }
        },transactionManager).build();
    }

    @Bean
    public Step exceptionedTasklet(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("exceptionedTasklet",jobRepository).tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                String jobParam = chunkContext.getStepContext().getJobParameters().get("taskletParam").toString();
                log.info("[TaskletJob] Execute exceptionedTasklet with taskletParam:{}",jobParam);
                return RepeatStatus.FINISHED;
            }
        },transactionManager).build();
    }

    @Bean
    public Step listeningTasklet(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("listeningTasklet",jobRepository).tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                log.info("[TaskletJob] Execute listeningTasklet");
                return RepeatStatus.FINISHED;
            }
        },transactionManager).listener(getMockListener()).build();
    }





    @Bean
    public Job taskletJob(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new JobBuilder("taskletJob",jobRepository)
                .start(repeatableTasklet(jobRepository,transactionManager))
                //next step
                .next(listeningTasklet(jobRepository,transactionManager))
                .on("ODD").fail()
                //if failed then retry as end, NOOP	All steps already completed or no steps configured for this job.
                .on("EVEN")
                    .to(failableTasklet(jobRepository,transactionManager))
                    //handle failed scenario
                    .on("FAILED").to(backupTasklet(jobRepository,transactionManager))
                    //implement custom decider
                    .on("COMPLETED").to(getExceptiondecider())
                        .on("EXCEPTIONED")
                        .to(exceptionedTasklet(jobRepository,transactionManager)).end()
                .build();

        //NOTES: "backup" step will mark job as completed (i.e. cannot retry)
    }

    @Bean
    public JobExecutionDecider getExceptiondecider(){
        return new ExceptionDecider();
    }

    @Bean
    public MockListener getMockListener(){
        return new MockListener();
    }

}