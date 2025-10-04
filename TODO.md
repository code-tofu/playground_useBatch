# TO ADMIN
- [/] Delete use batch archive
  - [ ] Review https://github.com/code-tofu/archive_useBatch for multitable batch
- [ ] Review todos
- [ ] Revise use batch and annotate notes 
- [ ] Merge archive_useBatch on Surface
- [ ] Review Zilant-ledger for long polling for TWS job scheduler
# TO CODE
- [ ] Add flyway
- [x] Using Tasklets
- [ ] Using Chunk Context and Step Context
  - [x] Reading from CSV Files
  - [ ] Writing to CSV Files
  - [ ] Reading from DB
  - [ ] Writing to DB
  - [ ] Read/Writing to Multiple Tables in DB
  - [ ] Processors
  - [ ] Filtering in Processors
  - [ ] Validation in Processors
- [x] Implement listeners
- [ ] Implement Job Logic
    - [x] batch statuses and conditional flow transitions
    - [x] conditional flow
    - [x] decider (based on job params)
    - [x] external/internal flows and (nested) job steps for reusability
    - [ ] split flows
    - [ ] parallel flows
    - [ ] skips for exception handling
    - [ ] batch status control using end fail stop transitions
    - [ ] restarting the job that is failed or stopped at a particular step
    - [ ] different batch job and step exit statuses
- [ ] Generate batch data using python
- [ ] add headers to DelimitedLineAggregator
- [ ] Reading/Writing multiple JPA tables using composite writer
- [ ] Multi thread reading from database using paging item reader
- [ ] Retries
- [ ] Using Quartz to trigger jobs
# TO VIDEO
- [ ] The full guide to Batch processing with Spring boot | Full guide | Ali https://www.youtube.com/watch?v=X48mMxEMYps
- [ ] Let's code: Spring Batch, part 1 Coffee + Software https://www.youtube.com/watch?v=rz1l2fpZVJQ&list=PL_HF_bzvfUwZsRO-FsjSXBU6uVOYWwGE-
- [x] Review alibou  https://github.com/ali-bouali/spring-batch-demo/tree/main/src/main/java/com/alibou/batch
  - [ ] Parallel processing
  - [ ] Controller as Job Launcher
- [ ] Review https://www.linkedin.com/learning/spring-spring-batch runjob.sh


# JDBC
- ddl-auto types
- hibernate.format_sql
- initialize-schema=embedded
Use jdbctemplate - https://docs.spring.io/spring-batch/reference/readers-and-writers/database.html#JdbcCursorItemReader
Named parameters


