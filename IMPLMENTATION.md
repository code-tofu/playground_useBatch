# IMPLEMENTATION

## Include Dependencies
- Flyway
- sql drivers
- Spring Batch
- Spring Data JPA

## Configure Spring
- enable batch processing (for older versions)
- create jobRepository and initialise job params tables

Configure job repo and jdbc properties
Create spring batch tables using Spring.batch.initialize-schema=always
Configure Beans

define entities and models
job
steps
job flows, flow logic
chunk beans: read/process/output

