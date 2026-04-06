# Stakeholders and Context Analysis

## Stakeholders

| Name                    | Role                     | Interest                                                                                                                                       |
|-------------------------|--------------------------|------------------------------------------------------------------------------------------------------------------------------------------------|
| End Users (Customers)   | Users of the application | Upload and access invoices digitally, track return deadlines automatically, and monitor personal spending through analytics                    |
| Project Owner (Student) | Developer & designer     | Successfully complete the final project and demonstrate full-stack software development skills using Java, Angular, AI, and cloud technologies |
| Trainers / Evaluators   | Reviewers                | Assess project quality, architecture, code structure, and documentation                                                                        |
| Retailers (Indirect)    | Invoice issuers          | Provide invoices that can be photographed, scanned, and stored digitally by customers                                                          |

---

## Business Context

IntelliInvoice exists to solve a common real-world problem: customers lose paper receipts
before they can use them for returns, warranty claims, or expense tracking.

**Invoices are often:**

* Lost within days of purchase
* Damaged, faded, or unreadable over time
* Forgotten along with their return deadlines and store policies

**As a result, customers are forced to:**

* Keep unwanted or defective products they cannot return
* Miss return windows due to forgotten deadlines
* Manually track spending across multiple stores with no overview

**IntelliInvoice solves this by:**

* Allowing users to upload a photo of any receipt — the AI does the rest
* Automatically extracting store name, date, total amount, VAT, invoice number,
  and line items using Claude Sonnet AI
* Storing the original invoice image securely in AWS S3
* Tracking return deadlines and showing days remaining per invoice in real time
* Providing monthly and yearly spending analytics with interactive charts

The system is developed as a student project within an academic environment and evaluated
as part of a Software Development course at WIFI Vienna. Despite its academic scope,
it addresses a genuine retail problem faced by consumers in physical stores every day.

---

## Technical Context

IntelliInvoice was developed as a final project for the Software Development course
at WIFI Vienna over approximately two months by a single developer.
The system demonstrates practical full-stack software engineering using modern
Java and web technologies.

### Technologies and Tools

| Layer           | Technology                    | Purpose                               |
|-----------------|-------------------------------|---------------------------------------|
| Backend         | Java 21 + Quarkus 3.2.3       | REST API and business logic           |
| AI              | Claude Sonnet via LangChain4j | Invoice data extraction from images   |
| Cloud Storage   | AWS S3 (eu-north-1)           | Storing original invoice images       |
| Database        | PostgreSQL                    | Persisting invoice and item data      |
| Frontend        | Angular 21 + Angular Material | User interface                        |
| Charts          | Chart.js                      | Spending analytics visualisation      |
| Build           | Maven (multi-module)          | Builds backend and frontend together  |
| IDE             | IntelliJ IDEA                 | Development environment               |
| Version Control | Git + GitHub                  | Source control and project management |

### Project Structure

```
intelli-invoice-parent/
├── backend/    ← Quarkus REST API + AI + AWS S3
└── frontend/   ← Angular 21 web interface
```

### Constraints

* Developed by a single developer within academic deadlines
* AI extraction depends on image quality — blurry or dark photos may reduce accuracy
* No access to real-world retail datasets or banking systems
* AI features are implemented at prototype level and are not production-ready
* The system is intended for personal purchases made in physical stores
  and is not designed for business or commercial invoice management
* Return policies are extracted by AI and may vary by store — not legally binding

---

## Risks and Assumptions

### Risks

| Risk                                  | Impact                 | Mitigation                                    |
|---------------------------------------|------------------------|-----------------------------------------------|
| Invoice image is blurry or unreadable | AI cannot extract data | Validation rejects incomplete extractions     |
| AI returns incorrect totals           | Wrong data saved       | Math validation: items + VAT must equal total |
| AWS S3 unavailable                    | Upload fails           | Error handling returns clear message to user  |
| External API rate limits              | AI extraction delayed  | Caught and reported via custom error codes    |
| Single developer bottleneck           | Delays in delivery     | Prioritized core features first               |

### Assumptions

* Users upload reasonably clear and readable invoice images
* Stores accept digital invoice records for returns or exchanges
* Internet access is available when using the application
* The system is used for personal invoice management, not legal or tax purposes
* Return policies shown are informational only and may differ from store policy