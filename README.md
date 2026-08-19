# EasyBankApi

EasyBankApi is an API REST for Easybank's backend, a web app for bank transactions and other payments using Spring Boot 4 and Java 25.

## Installation

Clone this repo to your computer:

```bash
git clone https://github.com/Juanlu3095/Easybank-API.git
```

Make sure you use a PostgreSQL database. This project uses it.

Create a secrets.properties file in src/main/resources with the environment variables using secrets.properties.example.

## Execution

Run this command to download dependencies in pom.xml if not and the app will start:

```bash
mvnw spring-boot:run
```

"mvnw" is used for the wrapper in case you don´t have Maven installed in your machine.

For Linux/Bash (Careful with chmod permissions!):

```bash
./mvnw spring-boot:run
```

## Testing

Must create a different PostgreSQL database than the one used for production or development. Once is done, create a file test/resources/application-test.properties with database
config the same way as in main folder. The name of the database to create for testing is given here. Then, in each test class must add the annotation @ActiveProfiles("test")
for telling Spring which environment to use. Use the next command to run all tests:

```bash
mvnw test
```

For running a specific test class:

```bash
mvnw test -Dtest=MessageTest
```

where "MessageTest" is the name of the class where the individual tests are.

To run a specific test in a class:

```bash
mvnw test -Dtest=MessageTest#createMessage
```

where "MessageTest" is the class name and "createMessage" is the method with @Test annotation to run.

## Deploy

You can compile this project with the maven command:

```bash
mvnw package
```

If you need to clean the target directory, this command will delete every file in that folder and compile:

```bash
mvnw clean package
```

With this, tests will be executed and if successful, the compiled .jar file will be available in target folder. To execute it:

```bash
java -jar target/easybank_api-0.0.1-SNAPSHOT.jar
```

## EER Diagram

This diagram shows database tables and their relationships:

![EER Diagram](https://drive.google.com/uc?export=view&id=1ygWpe75EsFyiQ5__-FpM9oaQ4kv6gZNn)