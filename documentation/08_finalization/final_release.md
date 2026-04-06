# Final Release

## Release Information

**Version:** 1.0.0
**Release Date:** 2026-04-02
**Release Type:** Major
**Author:** Madeha Mohammed
**Course:** Software Development with Java — WiFi Vienna

---

## Release Overview

IntelliInvoice 1.0.0 is the first and final release of the system,
delivered as a student final project. The system allows users to
upload invoice images, automatically extract structured data using
Claude Sonnet AI, track return deadlines, and visualise spending
analytics through an Angular web interface.

---

## Features Delivered

| Feature                               | Status |
|---------------------------------------|--------|
| Invoice upload and AI extraction      | Done   |
| Invoice list with search and filter   | Done   |
| Invoice detail with original S3 image | Done   |
| Delete invoice                        | Done   |
| Return deadline tracking              | Done   |
| Spending analytics charts             | Done   |

---

## Release Metrics

| Metric                       | Value |
|------------------------------|-------|
| Total user stories delivered | 12    |
| Total story points delivered | 46    |
| Total test cases executed    | 20    |
| Test pass rate               | 100%  |
| Critical bugs                | 0     |

---

## Software Components

| Component     | Technology         | Version         |
|---------------|--------------------|-----------------|
| Backend       | Java + Quarkus     | 21 + 3.2.3      |
| Frontend      | Angular + Material | 21              |
| Database      | PostgreSQL         | 15.x            |
| AI Service    | Claude Sonnet      | claude-3-sonnet |
| Cloud Storage | AWS S3             | eu-north-1      |
| Build tool    | Maven              | 3.x             |

---

## Known Issues and Limitations

| Issue                                    | Severity | Workaround                               |
|------------------------------------------|----------|------------------------------------------|
| Camera capture not supported             | Low      | Upload existing file from device         |
| No user authentication                   | Medium   | Single user system only                  |
| AI accuracy depends on image quality     | Low      | Use clear well-lit invoice photos        |
| File size limit not enforced on frontend | Low      | Backend validation catches invalid files |

---

## System Requirements

- **OS:** macOS or Windows with Java 21 installed
- **Browser:** Chrome, Firefox, or Edge
- **Database:** PostgreSQL running locally
- **Environment variables:** AWS and Anthropic credentials required

---

## How to Run

```bash
# Terminal 1 — backend
cd backend
./mvnw quarkus:dev

# Terminal 2 — frontend
cd frontend
npm start

# Production build
mvn package
```

---

## Post-Deployment Verification

- [ ] Quarkus starts on http://localhost:8080
- [ ] Angular starts on http://localhost:4200
- [ ] Invoice upload works and AI extraction succeeds
- [ ] Invoice list loads from PostgreSQL
- [ ] Analytics charts render correctly

---

## Future Releases — v2.0.0 (Planned)

- JWT authentication and multi-user support
- Camera capture for mobile devices
- AI purchase recommendations
- Flyway database migrations
- Cloud deployment with CI/CD pipeline

---

## Release Approval

**Status:** Pending trainer evaluation
**Decision Maker:** WiFi Vienna Trainers
**Date:** 2026-04-10