# Setup Guide — IntelliInvoice

**Last Updated:** April 2026
**Author:** Madeha Mohammed

---

## Prerequisites

| Tool          | Version | Verify           |
|---------------|---------|------------------|
| Java JDK      | 21      | `java -version`  |
| Maven         | 3.6+    | `mvn -version`   |
| Node.js       | 22+     | `node --version` |
| Git           | any     | `git --version`  |
| PostgreSQL    | 15.x    | running locally  |
| IntelliJ IDEA | any     | —                |

You also need:

- **AWS account** with an S3 bucket created
- **Anthropic API key** for Claude Sonnet access

---

## Clone the Repository

```bash
git clone https://github.com/Madeha9/ai_agent_model.git
cd ai_agent_model
```

---

## Database Setup

1. Install PostgreSQL and open pgAdmin or psql
2. Create the database:

```sql
CREATE DATABASE intelliinvoice;
```

3. Hibernate will create the tables automatically on first run —
   no manual SQL scripts needed.

---

## Environment Variables

Set these in **IntelliJ Run Configuration** →
Edit Configurations → Environment Variables:

AWS_ACCESS_KEY_ID = your_aws_access_key
AWS_SECRET_ACCESS_KEY = your_aws_secret_key
AWS_REGION = eu-north-1
AWS_BUCKET_NAME = intelliinvoice-files-wifi-2026

Also add your database connection in
`backend/src/main/resources/application.properties`:

```properties
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=postgres
quarkus.datasource.password=your_password
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/intelliinvoice
quarkus.hibernate-orm.database.generation=update
```

> Never commit real credentials to Git.
> Keep AWS keys in IntelliJ environment variables only.

---

## Running the Application

### Development mode — two terminals

```bash
# Terminal 1 — Quarkus backend
cd backend
./mvnw quarkus:dev
# runs on http://localhost:8080
# Swagger UI: http://localhost:8080/q/swagger-ui

# Terminal 2 — Angular frontend
cd frontend
npm install
npm start
# runs on http://localhost:4200
```

### Production build

```bash
# Builds backend + frontend into a single JAR
mvn package

# Run the JAR
java -jar backend/target/intelli-invoice-backend-1.0.0-SNAPSHOT-runner.jar
```

---

## IDE Configuration — IntelliJ IDEA

1. **Open project:**
   File → Open → select the root `pom.xml`
   IntelliJ detects the Maven multi-module structure automatically

2. **Set JDK:**
   File → Project Structure → SDK → Java 21

3. **Set environment variables:**
   Edit Configurations → select Quarkus run config
   → Environment Variables → add AWS keys

4. **Run backend:**
   Use the Maven panel → backend → Plugins → quarkus → quarkus:dev

5. **Run frontend:**
   Open terminal → `cd frontend` → `npm start`

---

## Troubleshooting

| Problem                   | Solution                                                   |
|---------------------------|------------------------------------------------------------|
| Port 8080 already in use  | `lsof -i :8080` then kill the process                      |
| Database connection fails | Check PostgreSQL is running and credentials are correct    |
| AWS upload fails          | Check environment variables are set in IntelliJ run config |
| Angular install fails     | Run `npm install` inside the `frontend` folder             |
| Maven build fails         | Run `mvn clean install -U` to force update dependencies    |
| CORS error in browser     | Make sure `CorsFilter.java` exists in the restapi package  |

---

## Related Documentation

- [Architecture Overview](architecture/overview.md)
- [API Documentation](api/endpoints.md)
- [ADR-001](../../documentation/07_decisions/adr_001_architecture_decision.md)
- [ADR-002](../../documentation/07_decisions/adr_002_technology_selection.md)