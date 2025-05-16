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
- [ ] Review Zilant-ledger for long polling for TWS job scheduler
- [ ] Generate batch data using python
- [ ] Review https://www.linkedin.com/learning/spring-spring-batch
  - p ] runjob.sh
- [x] Review alibou  https://github.com/ali-bouali/spring-batch-demo/tree/main/src/main/java/com/alibou/batch
  - [ ] Parallel processing
  - [ ] Controller as Job Launcher
- [ ] add headers to DelimitedLineAggregator

- ddl-auto types
- hibernate.format_sql
- initialize-schema=embedded


Multi thread reading from database using paging item reader
Use jdbctemplate - https://docs.spring.io/spring-batch/reference/readers-and-writers/database.html#JdbcCursorItemReader
Named parameters
Retries
Using Quartz