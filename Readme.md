# IntelliInvoice — AI-Powered Invoice Management System

> Upload a receipt, let Claude AI extract the data, track return
> deadlines, and analyse your spending — all automatically.

---

## Overview

IntelliInvoice is a full-stack invoice management system developed
as a final project for the Software Development course at WiFi Vienna.
Users upload invoice images or PDFs, Claude Sonnet AI automatically
extracts all relevant data, and the system tracks return deadlines
and provides spending analytics through a modern Angular web interface.

**The main focus of this project is the Java/Quarkus backend** including
REST API design, AI integration, cloud storage, math validation, and
database mapping. The Angular frontend was built to demonstrate and
test the backend functionality.

---

## How it works

User uploads invoice image (JPG, PNG, PDF)  
↓  
File uploaded to AWS S3  
↓  
S3 URL sent to Claude Sonnet AI  
↓  
AI extracts: store, date, total, VAT, items  
↓  
Math validation: items + VAT = total  
↓  
Saved to PostgreSQL  
↓  
Displayed in Angular UI with return status

---

## Tech Stack

| Layer         | Technology                    | Version         |
|---------------|-------------------------------|-----------------|
| Backend       | Java + Quarkus                | 21 + 3.2.3      |
| AI Extraction | Claude Sonnet via LangChain4j | claude-3-sonnet |
| Cloud Storage | AWS S3                        | eu-north-1      |
| Database      | PostgreSQL + Hibernate JPA    | 15.x            |
| Frontend      | Angular + Angular Material    | 21              |
| Charts        | Chart.js                      | 4.4.1           |
| Build         | Maven multi-module            | 3.x             |

---

## Project Structure

```text
intelli-invoice-parent/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   └── java/at/madeha/intelliinvoice/
│   │   │       ├── restapi/
│   │   │       ├── domain/
│   │   │       ├── service/
│   │   │       ├── infrastructure/
│   │   │       ├── database/
│   │   │       ├── config/
│   │   │       └── exception/
│   │   ├── test/
│   │   │   └── java/at/madeha/intelliinvoice/
│   │   │       ├── service/
│   │   │       ├── infrastructure/
│   │   │       └── database/
│   └── pom.xml
├── frontend/
│   ├── src/app/
│   │   ├── pages/
│   │   ├── services/
│   │   └── models/
│   └── pom.xml
├── documentation/                  ← Project documentation
│   ├── Project overview
│   ├── Project management
│   ├── Analysis
│   ├── Design
│   ├── Testing
│   ├── Decisions
│   ├── Finalization
│   └── Database
├── README.md
└── pom.xml 
```

---

## Prerequisites

- Java 21
- Maven 3.6+
- Node.js 22+
- PostgreSQL running locally
- AWS account with S3 bucket
- Anthropic API key

---

## Environment Variables

Set these in IntelliJ Run Configuration — never store in code:

- AWS_ACCESS_KEY_ID = your_aws_access_key
- AWS_SECRET_ACCESS_KEY = your_aws_secret_key
- AWS_REGION = eu-north-1
- AWS_BUCKET_NAME = intelliinvoice-files-wifi-2026

---

## Quickstart

### Development mode
```bash
# Terminal 1 — Quarkus backend
cd backend
./mvnw quarkus:dev
# runs on http://localhost:8080

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
```

---

## API Endpoints

| Method   | Endpoint                            | Description                      |
|----------|-------------------------------------|----------------------------------|
| `POST`   | `/UploadInvoice`                    | Upload and process invoice image |
| `GET`    | `/myinvoices`                       | Get all invoices                 |
| `GET`    | `/myinvoices/{id}`                  | Get invoice by ID                |
| `GET`    | `/myinvoices/search?name=`          | Search by store name             |
| `PUT`    | `/myinvoices`                       | Update invoice                   |
| `DELETE` | `/myinvoices/{id}`                  | Delete invoice                   |
| `GET`    | `/myinvoices/spending?year=&month=` | Monthly spending total           |

---

## Features

- AI-powered data extraction — zero manual entry
- AWS S3 cloud storage for original invoice images
- Return deadline tracking with days remaining
- Invoice status: RETURNABLE / NON_RETURNABLE / SATISFIED
- Search and filter invoices by store name and status
- Monthly, quarterly, and top stores spending analytics
- Math validation: line items + VAT must equal total
- Custom error handling with ErrorCode enum

---

## Known Limitations

- No user authentication — single user system
- Camera capture not supported — file upload only
- AI accuracy depends on invoice image quality

---

## Future Roadmap

- JWT authentication and multi-user support
- Camera capture for mobile devices
- AI purchase recommendations
- Flyway database migrations
- CI/CD pipeline and cloud deployment

---

## Author

**Madeha Mohammed A.I**
Software Development with Java — WiFi Vienna, 2026

---

## License

This project was developed as a student final project
for academic evaluation purposes only.