package code.tofu.useBatch.chunk;

import code.tofu.useBatch.chunk.entity.PurchaseOrder;
import code.tofu.useBatch.chunk.model.FlatFileInput;
import code.tofu.useBatch.chunk.repository.PurchaseOrderRepository;
import code.tofu.useBatch.tasklet.TaskletFlowConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class ChunkJobConfig {

    @Value("${chunk.flatfile.headers}")
    String[] flatfileHeaders;

    @Value("${chunk.flatfile.file-location}")
    String fileLocation;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    TaskletFlowConfig externalTasketFlow;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Bean
    public Job chunkJob(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new JobBuilder("chunkJob",jobRepository)
                .start(chunkReadFromFileIntoDBStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step chunkReadFromFileIntoDBStep(){
        return new StepBuilder("csvImportIntoDB",jobRepository)
                //specify the type parameter so that the fluent chain can infer
                .<FlatFileInput, PurchaseOrder>chunk(5,transactionManager) //<Input, Output>
                .reader(flatFileInputItemReader())
                .processor(orderProcessor())
                .writer(processedTransactionWriter())
                .build();
    }

    @Bean
    public FlatFileItemReader<FlatFileInput> flatFileInputItemReader() {
        FlatFileItemReader<FlatFileInput> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource(fileLocation));
        itemReader.setName("csvOrderReader");
        itemReader.setLinesToSkip(1); //headerRow

        DefaultLineMapper<FlatFileInput> inputLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setStrict(false);
        tokenizer.setNames(flatfileHeaders);

        inputLineMapper.setLineTokenizer(tokenizer);
        inputLineMapper.setFieldSetMapper(new FlatFileInputFieldSetMapper());

        itemReader.setLineMapper(inputLineMapper);

        return itemReader;
    }

    @Bean
    public PurchaseOrderProcessor orderProcessor() {
        return new PurchaseOrderProcessor();
    }


    @Bean
    public RepositoryItemWriter<PurchaseOrder> processedTransactionWriter() {
        RepositoryItemWriter<PurchaseOrder> writer = new RepositoryItemWriter<>();
        writer.setRepository(purchaseOrderRepository);
        writer.setMethodName("save");
        return writer;
    }


}
