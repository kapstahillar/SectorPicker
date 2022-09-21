
# Spring sector picker

## Introduction
This is a simple project using Java Spring Boot project using Gradle.

## Requirements
1.  JDK 17
2. Command line
3. (Optional) Phyton


## Quick start

0. (Optional) Run sector-import-tool.py to generate sectors import sql from index file to "src/main/resources/data.sql" file
```
 py sector-import-tool.py
```

1. Run project 
```
./gradlew bootRun
```

Project starts at localhost:8080. H2 console is accessible at localhost:8080/h2-console.

For testing run 
```
./gradlew test
```
