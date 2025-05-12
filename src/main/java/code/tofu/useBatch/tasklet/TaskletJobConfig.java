package code.tofu.useBatch.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Date;

@Configuration
@Slf4j
public class TaskletJobConfig {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    TaskletFlowConfig externalTasketFlow;

    @Bean
    public Job taskletJob(){
        return new JobBuilder("taskletJob",jobRepository)
                .start(repeatableTasklet())
                //next step
                .next(listeningTasklet())
                .on("ODD")
                .fail()
//                    .end()
                //if failed then retry as end, NOOP	All steps already completed or no steps configured for this job.
                .on("EVEN")
                .to(externalTasketFlow.getTaskletFlow())
                .end()
                .build();

    }

    @Bean
    public Step nestedTaskletJobStep(){ //use the tasklet Job as a step instead
        return new StepBuilder("nestedTaskletJobStep", jobRepository)
                .job(taskletJob()).build();
    }

    @Bean
    public Step repeatableTasklet(){

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
                return RepeatStatus.FINISHED;
            }
        },transactionManager).build();
    }

    @Bean
    public Step listeningTasklet(){
        return new StepBuilder("listeningTasklet",jobRepository).tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                log.info("[TaskletJob] Execute listeningTasklet");
                return RepeatStatus.FINISHED;
            }
        },transactionManager).listener(getMockListener()).build();
    }

    @Bean
    public MockListener getMockListener(){
        return new MockListener();
    }

}