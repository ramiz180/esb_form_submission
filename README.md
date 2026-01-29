# ESB Networks Form Automation

## Overview

This project is a robust Java-based test automation framework designed to automate the submission of Renewable Connection forms on the ESB Networks portal. It utilizes **Selenium WebDriver** for browser interaction, **TestNG** for test execution and management, and the **Page Object Model (POM)** design pattern for maintainable and scalable code.

The framework automates the end-to-end process, including:
*   User Login
*   Filling and submitting **Form 001** (Customer & Installer Details)
*   Filling and submitting **Form 002** (Technical Details & Inverter Configuration)
*   Filling and submitting **Form 003** (Post-Commissioning Settings)
*   Handling File Uploads (Certificates and Datasheets)

## Tech Stack

*   **Language:** Java 11
*   **Automation Tool:** Selenium WebDriver (v4.27.0)
*   **Test Runner:** TestNG (v7.10.2)
*   **Build Tool:** Maven
*   **Logging:** Log4j2
*   **Design Pattern:** Page Object Model (POM)

## Prerequisites

Ensure you have the following installed on your local machine:

*   **Java Development Kit (JDK) 11** or higher.
*   **Apache Maven** (v3.x).
*   **Google Chrome** browser (latest version).

## Installation

1.  **Clone the repository :**
    ```bash
    git clone <repository_url>
    cd esb_form_submission
    ```

2.  **Install dependencies:**
    ```bash
    mvn clean install -DskipTests
    ```

## Configuration

The project uses a `config.properties` file for managing application URLs, credentials, and timeout settings.

1.  Navigate to `src/main/resources/config.properties`.
2.  Update the file with your specific testing credentials and path configurations.

**Template `config.properties`:**

```properties
# Website Configuration
base.url=https://newconnections.esbnetworks.ie/
login.url=https://newconnections.esbnetworks.ie/RenewableConnection/RenewableConnectionType
form.url=https://newconnections.esbnetworks.ie/RenewableConnection/RenewableConnectionType

# Credentials
username=your_email@example.com
password=your_secure_password

# Timeout (in seconds)
explicit.wait=20

# Browser Configuration
headless=false
```

> **Note:** Ensure strict confidentiality with your credentials. Do not commit real passwords to version control.

## Usage

### Running Tests via Maven

You can run the entire test suite using the Maven command line. This will execute the tests defined in `src/test/resources/testng.xml`.

```bash
mvn test
```

### Running Tests via IDE

1.  Import the project into your IDE (IntelliJ IDEA, Eclipse, VS Code) as a Maven project.
2.  Navigate to `src/test/resources/testng.xml`.
3.  Right-click `testng.xml` and select **Run 'testng.xml'**.
    *   Alternatively, you can run the test class directly: `src/test/java/com/esbnetworks/tests/FormAutomationTest.java`.

## Project Structure

The project follows a standard Maven directory structure with a clear separation of concerns using POM.

```
esb_form_submission/
├── logs/
│   └── automation.log          # Runtime execution logs
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/esbnetworks/automation/
│   │   │       ├── base/       # BaseTest (Driver setup) & RetryAnalyzer
│   │   │       ├── pages/      # Page Objects (LoginPage, RenewableConnectionPage)
│   │   │       └── utils/      # Utilities (ConfigReader)
│   │   └── resources/
│   │       ├── config.properties # Configuration file
│   │       └── log4j2.xml      # Log4j2 logging configuration
│   └── test/
│       ├── java/
│       │   └── com/esbnetworks/tests/ # Test Scripts (FormAutomationTest)
│       └── resources/
│           └── testng.xml      # TestNG Suite configuration
├── pom.xml                     # Maven POM file (Dependencies & Plugins)
└── README.md                   # Project Documentation
```

## Logs & Reporting

*   **Console Output:** Real-time execution logs are printed to the console.
*   **Log File:** Detailed logs are saved to `logs/automation.log` for debugging and historical analysis.
*   **TestNG Reports:** After execution, TestNG generates HTML reports in the `target/surefire-reports` directory (e.g., `emailable-report.html`, `index.html`).

## Troubleshooting

*   **File Upload Issues:** Ensure the file paths in `FormAutomationTest.java` point to valid files on your local machine if you are not using the parameterized approach.
*   **ElementNotInteractableException:** Try increasing the `explicit.wait` in `config.properties` or checking if an overlay/cookie banner is blocking the element.

## License

[Add License Information Here]
