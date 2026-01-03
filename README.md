# ESB Form Submission

This project is a migration of the existing Python Selenium automation to a Java-based framework using Maven, TestNG, Log4j2, and the Page Object Model (POM).

## Project Structure

```
java_project/
├── pom.xml                     # Maven dependencies and build config
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/esbnetworks/automation/
│   │   │       ├── base/       # BasePage, BaseTest
│   │   │       ├── pages/      # Page Objects (LoginPage, RenewableConnectionPage)
│   │   │       └── utils/      # Utilities (ConfigReader)
│   │   └── resources/
│   │       ├── log4j2.xml      # Logging configuration
│   │       └── config.properties # URLs and Credentials
│   └── test/
│       ├── java/
│       │   └── com/esbnetworks/tests/ # Test classes
│       └── resources/
│           └── testng.xml      # TestNG Suite configuration
```

## Prerequisites

- Java JDK 11 or higher
- Maven 3.x
- Google Chrome installed

## Configuration

Update `src/main/resources/config.properties` with your credentials and configuration:

```properties
username=your_email@example.com
password=your_password
```

## Running Tests

To run the tests using Maven:

```bash
mvn test
```

This will execute the test suite defined in `src/test/resources/testng.xml`.

## Logs

Execution logs are saved to `logs/automation.log` and printed to the console.
