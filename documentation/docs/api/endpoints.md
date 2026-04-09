# API Documentation - IntelliInvoice

## 🚀 Overview

IntelliInvoice is a smart document processing system. It leverages **Quarkus** for the backend, **AWS S3** for secure
image storage, and **Claude 3.5 Sonnet (via LangChain4j)** to transform unstructured invoice images into structured
business data.

---

## 📍 Base URL

- **Local Development:** `http://localhost:8080`

## 🛠 Interactive Documentation

This project uses **SmallRye OpenAPI** to generate live documentation. You can test the endpoints directly from the
browser:

- **Swagger UI:** [http://localhost:8080/q/swagger-ui/](http://localhost:8080/q/swagger-ui/)
- **OpenAPI Specification:** [http://localhost:8080/q/openapi](http://localhost:8080/q/openapi)

---

## 📡 Core API Endpoints

### 1. Invoice Management

| Method   | Endpoint           | Description                               |
|:---------|:-------------------|:------------------------------------------|
| `POST`   | `/invoices/upload` | Upload image to S3 + AI Data Extraction   |
| `GET`    | `/invoices`        | List all invoices (Sorted by newest date) |
| `GET`    | `/invoices/{id}`   | Get specific invoice details              |
| `DELETE` | `/invoices/{id}`   | Remove invoice and delete image from S3   |

### 2. Search & Filter

| Method | Endpoint           | Description                                     |
|:-------|:-------------------|:------------------------------------------------|
| `GET`  | `/invoices/search` | Search by store name (Query param: `?name=...`) |

---

## Data Schema (JSON)

### Invoice Object Example

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "storeName": "Alpine Electronics",
  "invoiceDate": "2026-04-02",
  "totalAmount": 765.49,
  "currency": "EUR",
  "status": "RETURNABLE",
  "daysLeft": 12,
  "imageUrl": "[https://intelli-invoice-bucket.s3.amazonaws.com/inv_001.png](https://intelli-invoice-bucket.s3.amazonaws.com/inv_001.png)"
} 
```

## Tech Stack Mapping

| Layer             | Technology                                              |
|:------------------|:--------------------------------------------------------|
| **Frontend**      | **Angular 18** (Signals, Material Design, SCSS)         |
| **Backend**       | **Quarkus 3.x** (Java 21, Jakarta EE)                   |
| **AI Engine**     | **Anthropic Claude 3.5 Sonnet** (OCR & Data Extraction) |
| **AI Framework**  | **LangChain4j** (Orchestration & Prompt Engineering)    |
| **Cloud Storage** | **AWS S3** (Hosting Original Invoice Images)            |
| **Persistence**   | **Hibernate ORM** with **PostgreSQL**                   |
| **API Spec**      | **SmallRye OpenAPI** (Swagger UI)                       |

---

## System Architecture Flow

1. **Frontend:** User uploads an image via Angular.
2. **Cloud:** The image is stored securely in an **AWS S3 Bucket**.
3. **AI Layer:** **LangChain4j** sends the image to **Claude 3.5 Sonnet** to extract text and numbers.
4. **Backend:** **Quarkus** receives the AI data, calculates the return deadline, and saves metadata to **PostgreSQL**.
5. **UI:** The invoice appears instantly in the Angular table with its status (Returnable/Expired).