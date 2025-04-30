- spring-web-starter will conflict with spring-batch during startup
- @EnableBatchProcessing is no longer required as new behaviour tells autoconfig to back off 

- Specify in Program Arguments(i.e. args): id=1 taskletParam=3

- job-instance-id -> job-execution-id -> step-execution-id
- BATCH_JOB_INSTANCE, BATCH_JOB_EXECUTION, BATCH_STEP_EXECUTION
  - If a step fails, retrying will keep the same job-instance-id. job-execution-id will change
  - A job execution whose status is ABANDONED will not be restarted by the framework

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


- next 
- on - if equals
- from - else if
- to - then