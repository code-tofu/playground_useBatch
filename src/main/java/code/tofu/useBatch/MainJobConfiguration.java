package code.tofu.useBatch;

import code.tofu.useBatch.chunk.InputChunkJobConfig;
import code.tofu.useBatch.chunk.OutputChunkJobConfig;
import code.tofu.useBatch.tasklet.TaskletJobConfig;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class MainJobConfiguration {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    TaskletJobConfig taskletJobConfig;

    @Autowired
    InputChunkJobConfig inputChunkJobConfig;

    @Autowired
    OutputChunkJobConfig outputChunkJobConfig;

    @Bean
    public Job mainTaskletJob(){
        return new JobBuilder("mainJob",jobRepository)
                .start(inputChunkJobConfig.nestedInputChunkJobStep())
                .next(outputChunkJobConfig.nestedOutputChunkJobStep())
                .next(taskletJobConfig.nestedTaskletJobStep())
                .build();
    }

}
