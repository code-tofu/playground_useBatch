# playground_useBatch
To play around with and learn Spring Batch

## Versions 
### v1.0.0
- Using tasklets for conditional flows
- Listeners(before and after) and deciders
- using Step Context
### v2.0.0
- Using chunk processing to read a list of orders and tabulating the total per order into the DB
### v2.1.0
- Using chunk processing to read purchase orders from db and write to file
### v2.2.0
- setup Flyway for db initialisation
- combine all jobs into one main job
- add validation processor