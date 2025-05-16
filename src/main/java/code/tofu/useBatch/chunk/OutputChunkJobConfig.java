package code.tofu.useBatch.chunk;

import code.tofu.useBatch.chunk.entity.PurchaseOrder;
import code.tofu.useBatch.chunk.model.FlatFileOutput;
import code.tofu.useBatch.chunk.repository.PurchaseOrderRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Date;


@Configuration
public class OutputChunkJobConfig {

    @Value("${chunk.output-file.headers}")
    String[] outputFlatFileNames;

    @Value("${chunk.output-file.location}")
    String outputFileLocation;

    @Value("${chunk.output-file.name-prefix}")
    String outputFileNamePrefix;

    public static String SELECT_SQL="SELECT purchase_order_number, order_datetime, vendor_id, total_cost\n" +
            "FROM use_batch.purchase_orders order by purchase_order_number;";

    @Autowired
    JobRepository jobRepository;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    DataSource defaultDatasource;

    @Bean
    public Job outputChunkJob(){
        return new JobBuilder("outputChunkJob",jobRepository)
                .start(chunkWriteFromDBToFileStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step nestedOutputChunkJobStep(){ //use the tasklet Job as a step instead
        return new StepBuilder("nestedOutputChunkJobStep", jobRepository)
                .job(outputChunkJob()).build();
    }

    @Bean
    public Step chunkWriteFromDBToFileStep(){
        return new StepBuilder("writeFromDBToFile ",jobRepository)
                .<PurchaseOrder, FlatFileOutput>chunk(5,transactionManager) //<Input, Output>
                .reader(purchaseOrderItemReader())
                .processor(purchaseOrderOutputProcessor())
                .writer(flatFileItemWriter())
                .build();
    }

    @Bean
    public PurchaseOrderOutputProcessor purchaseOrderOutputProcessor() {
        return new PurchaseOrderOutputProcessor();
    }

    @Bean
    public JdbcCursorItemReader<PurchaseOrder> purchaseOrderItemReader() {
        return new JdbcCursorItemReaderBuilder<PurchaseOrder>().dataSource(defaultDatasource)
                .name("purchaseOrderItemReader").sql(SELECT_SQL)
                .rowMapper(new PurchaseOrderRowMapper())
                .build();
    }

    @Bean
    public ItemWriter flatFileItemWriter(){
        DelimitedLineAggregator<FlatFileOutput> aggregator = new DelimitedLineAggregator<>();
        aggregator.setDelimiter("|");

        BeanWrapperFieldExtractor<FlatFileOutput> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(outputFlatFileNames);
        aggregator.setFieldExtractor(extractor);

        FlatFileItemWriter< FlatFileOutput> itemWriter = new FlatFileItemWriter<>();

        itemWriter.setResource(new FileSystemResource(outputFileLocation + "/" + outputFileNamePrefix + new Date().getTime() + ".txt"));
        itemWriter.setLineAggregator(aggregator);
        return itemWriter;

    }


}
