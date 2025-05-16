- spring-web-starter will conflict with spring-batch during startup
- @EnableBatchProcessing is no longer required as new behaviour tells autoconfig to back off 

- Specify in Program Arguments(i.e. args): id=1 taskletParam=3

- job-instance-id -> job-execution-id -> step-execution-id
- BATCH_JOB_INSTANCE, BATCH_JOB_EXECUTION, BATCH_STEP_EXECUTION
  - If a step fails, retrying will keep the same job-instance-id. job-execution-id will change
  - A job execution whose status is ABANDONED will not be restarted by the framework

https://github.com/spring-projects/spring-batch/wiki/Spring-Batch-5.0-Migration-Guide#jobbuilderfactory-and-stepbuilderfactory-bean-exposureconfiguration
- in Spring Batch 5, the factories were deprecated

https://docs.spring.io/spring-batch/reference/schema-appendix.html
- batch job execution params - related by job execution id
- batch job instance - instance id and job key
- batch step execution
  - by job execution id
  - version is for optimistic locking purposes
  - repeatable tasklet will keep updating the same step_execution_id, but version will be updated

https://docs.spring.io/spring-batch/reference/repeat.html
> A RepeatStatus enumeration conveys information to the caller of the repeat operations about whether any work remains.
> In the preceding example, we return RepeatStatus.CONTINUABLE, to show that 

Transitions
- next 
- on - if equals
- from - else if
- to - then

> Duplicate step detected in execution of job. If either step fails, both will be executed again on restart

- a tasklet will be repeated until the status is finished
- changing job name or tasklet CLI params name/value allows job to re-run (job parameters different)
- "backup" step will mark job as completed (i.e. cannot retry)

- jackson requires getter/setters
- parameterize the chunk so that the fluent chain can infer the types
- `writer.setMethodName("save")` will save/or update for same primary key

- ? Normalising a database

- without order by, if a job restarts it is not given that the read order from database will be the same
- how does flat file reader/writer remember where is the cursor?
- named query

- specify the column explicitly or jpa will auto infer based on camel case to snake case when using ddl-auto


