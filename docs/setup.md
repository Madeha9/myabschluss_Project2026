# Setup Guide

## Table of Contents

- [Prerequisites](#prerequisites)
- [Development Environment Setup](#development-environment-setup)
- [Database Setup](#database-setup)
- [Environment Variables](#environment-variables)
- [IDE Configuration](#ide-configuration)
- [Running the Application](#running-the-application)
- [Troubleshooting](#troubleshooting)

## Prerequisites

Before setting up the project, ensure you have the following installed:

### Required Software

- **Java Development Kit (JDK) 11 or higher**
  - Download from [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html) or [OpenJDK](https://openjdk.java.net/)
  - Verify installation: `java -version`

- **Apache Maven 3.6+**
  - Download from [Maven website](https://maven.apache.org/download.cgi)
  - Verify installation: `mvn -version`

- **Git**
  - Download from [Git website](https://git-scm.com/downloads)
  - Verify installation: `git --version`

### Optional Software

- **IDE:** IntelliJ IDEA, Eclipse, or VS Code
- **Database Tool:** DBeaver, MySQL Workbench, or pgAdmin (depending on your database)

## Development Environment Setup

### 1. Clone the Repository

```bash
git clone https://github.com/chris-cgsit/train_2025_java_aufbau_example_project.git
cd train_2025_java_aufbau_example_project
```

### 2. Install Dependencies

Maven will automatically download required dependencies:

```bash
mvn clean install
```

### 3. Verify Installation

Run tests to ensure everything is set up correctly:

```bash
mvn test
```

## Database Setup

### Option 1: Local Database

#### PostgreSQL Setup

1. **Install PostgreSQL:**
   - Download from [PostgreSQL website](https://www.postgresql.org/download/)
   - Follow installation instructions for your OS

2. **Create Database:**
   ```sql
   CREATE DATABASE train_project_db;
   CREATE USER train_user WITH PASSWORD 'your_password';
   GRANT ALL PRIVILEGES ON DATABASE train_project_db TO train_user;
   ```

3. **Connection Details:**
   - Host: `localhost`
   - Port: `5432`
   - Database: `train_project_db`
   - Username: `train_user`
   - Password: `your_password`

#### MySQL Setup

1. **Install MySQL:**
   - Download from [MySQL website](https://dev.mysql.com/downloads/)
   - Follow installation instructions for your OS

2. **Create Database:**
   ```sql
   CREATE DATABASE train_project_db;
   CREATE USER 'train_user'@'localhost' IDENTIFIED BY 'your_password';
   GRANT ALL PRIVILEGES ON train_project_db.* TO 'train_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

3. **Connection Details:**
   - Host: `localhost`
   - Port: `3306`
   - Database: `train_project_db`
   - Username: `train_user`
   - Password: `your_password`

### Option 2: In-Memory Database (H2)

For development and testing, you can use H2 in-memory database:

1. Add H2 dependency to `pom.xml` (if not already present)
2. No additional setup required - database is created automatically on application start

## Environment Variables

### Setting Up Environment Variables

Create a `.env` file in the project root (do not commit this file):

```bash
# Database Configuration
DB_HOST=localhost
DB_PORT=5432
DB_NAME=train_project_db
DB_USERNAME=train_user
DB_PASSWORD=your_password

# Application Configuration
APP_PORT=8080
APP_ENV=development

# Logging
LOG_LEVEL=DEBUG

# Security (if applicable)
JWT_SECRET=your_jwt_secret_key
JWT_EXPIRATION=86400000

# External Services (if applicable)
API_KEY=your_api_key
```

### Environment-Specific Configuration

Different configurations for different environments:

- **Development:** `application-dev.properties`
- **Testing:** `application-test.properties`
- **Production:** `application-prod.properties`

### Loading Environment Variables

#### Linux/macOS

```bash
export DB_HOST=localhost
export DB_PORT=5432
# ... other variables
```

Or source from file:
```bash
source .env
```

#### Windows (Command Prompt)

```cmd
set DB_HOST=localhost
set DB_PORT=5432
```

#### Windows (PowerShell)

```powershell
$env:DB_HOST="localhost"
$env:DB_PORT="5432"
```

## IDE Configuration

### IntelliJ IDEA

1. **Import Project:**
   - File → Open → Select project directory
   - IntelliJ will auto-detect Maven project

2. **Set JDK:**
   - File → Project Structure → Project SDK → Select JDK 11+

3. **Configure Maven:**
   - File → Settings → Build, Execution, Deployment → Build Tools → Maven
   - Set Maven home directory
   - Enable "Automatically download" for sources and documentation

4. **Code Style:**
   - File → Settings → Editor → Code Style → Java
   - Import code style from `CONTRIBUTING.md` guidelines
   - Set tab size: 4 spaces
   - Set line length: 120 characters

5. **Enable Lombok (if used):**
   - Install Lombok plugin
   - Enable annotation processing: Settings → Build, Execution, Deployment → Compiler → Annotation Processors

### Eclipse

1. **Import Project:**
   - File → Import → Maven → Existing Maven Projects
   - Select project directory

2. **Set JDK:**
   - Window → Preferences → Java → Installed JREs
   - Add JDK 11+

3. **Code Formatter:**
   - Window → Preferences → Java → Code Style → Formatter
   - Configure based on `CONTRIBUTING.md` guidelines

### Visual Studio Code

1. **Install Extensions:**
   - Java Extension Pack
   - Maven for Java
   - Debugger for Java

2. **Open Project:**
   - File → Open Folder → Select project directory

3. **Configure Java:**
   - Create `.vscode/settings.json`:
   ```json
   {
       "java.configuration.runtimes": [
           {
               "name": "JavaSE-11",
               "path": "/path/to/jdk-11"
           }
       ],
       "java.jdt.ls.java.home": "/path/to/jdk-11"
   }
   ```

## Running the Application

### Using Maven

```bash
# Compile
mvn compile

# Run (if main class is configured)
mvn exec:java

# Package
mvn package

# Run packaged JAR
java -jar target/train_2025_java_aufbau_example_project-1.0-SNAPSHOT.jar
```

### Using IDE

- **IntelliJ:** Right-click main class → Run
- **Eclipse:** Right-click main class → Run As → Java Application
- **VS Code:** Run → Start Debugging (F5)

### Development Mode

For continuous development with auto-reload (if configured):

```bash
mvn spring-boot:run  # For Spring Boot projects
# or
mvn compile exec:java  # For regular Java projects
```

## Troubleshooting

### Common Issues

#### Maven Build Fails

**Problem:** Dependencies cannot be downloaded

**Solution:**
```bash
# Clear Maven cache
mvn dependency:purge-local-repository

# Update dependencies
mvn clean install -U
```

#### Database Connection Fails

**Problem:** Cannot connect to database

**Solution:**
- Verify database is running
- Check connection credentials in environment variables
- Ensure firewall allows connections on database port
- Test connection using database client tool

#### Port Already in Use

**Problem:** Application port (e.g., 8080) is already in use

**Solution:**
```bash
# Find process using port (Linux/macOS)
lsof -i :8080

# Find process using port (Windows)
netstat -ano | findstr :8080

# Kill the process or change application port
```

#### OutOfMemoryError

**Problem:** Maven runs out of memory during build

**Solution:**
```bash
export MAVEN_OPTS="-Xmx1024m -XX:MaxPermSize=256m"
mvn clean install
```

### Getting Help

If you encounter issues not covered here:

1. Review project [Issues](https://github.com/chris-cgsit/train_2025_java_aufbau_example_project/issues)
2. Consult [Contributing Guide](../CONTRIBUTING.md)
3. Contact project maintainers

## Next Steps

After successful setup:

1. Read the [Architecture Overview](architecture/overview.md)
2. Review the [API Documentation](api/endpoints.md)
3. Follow the [User Guide](user-guide.md)
4. Check out [Contributing Guidelines](../CONTRIBUTING.md) to start developing

---

**Last Updated:** January 2026
