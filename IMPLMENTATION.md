# IMPLEMENTATION

## Include Dependencies
- Flyway
- sql drivers
- Spring Batch
- Spring Data JPA/JDBC

## Configure Spring
- enable batch processing (for older versions)
- create jobRepository, transactionManager and initialise job params tables (spring.batch.jdbc.initialize-schema)
- Create entity tables using flyway

## Configure Job and Steps Beans
- define job and step beans, flow logic
- define entities and models
- define chunk beans: reader/processor/writer

