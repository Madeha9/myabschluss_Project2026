# Test Data

## Overview

Test data for IntelliInvoice was collected from two sources:
real invoice images downloaded from the internet for upload testing,
and manually created data for database CRUD testing with JUnit.

---

## Test Data Set 1: Invoice Images (Upload Testing)

**Purpose:** Test the full upload and AI extraction flow
**Source:** Sample invoice images downloaded from the internet
**Used in:** TC-F-001, TC-I-001, TC-S-001

| File                   | Format | Content             | Expected Result                     |
|------------------------|--------|---------------------|-------------------------------------|
| receipt_mediamarkt.jpg | JPG    | Electronics receipt | AI extracts store, date, total, VAT |
| receipt_zara.png       | PNG    | Clothing receipt    | AI extracts store, date, total, VAT |
| invoice_test.pdf       | PDF    | Standard invoice    | AI extracts all fields              |
| blurry_image.jpg       | JPG    | Unreadable image    | INVALID_INVOICE_DATA error          |
| document.txt           | TXT    | Text file           | INVALID_FILE_FORMAT error           |

---

## Test Data Set 2: Database Test Data (JUnit)

**Purpose:** Test InvoiceRepository CRUD operations
**Source:** Manually created in JUnit test setup
**Used in:** TC-D-001, TC-D-002, TC-D-003, TC-D-004

```json
{
  "storeName": "MediaMarkt",
  "invoiceDate": "2025-03-10",
  "totalAmount": 249.90,
  "vatAmount": 41.65,
  "currency": "EUR",
  "invoiceNumber": 1042,
  "returnDays": 30,
  "items": [
    {
      "description": "Laptop charger",
      "quantity": 1,
      "unitPrice": 208.25,
      "lineTotal": 208.25
    }
  ]
}
```

---

## Invalid Test Data

| Test Case          | Input               | Expected Error        |
|--------------------|---------------------|-----------------------|
| Wrong file type    | document.txt        | INVALID_FILE_FORMAT   |
| Blurry image       | unreadable.jpg      | INVALID_INVOICE_DATA  |
| Math mismatch      | items + VAT ≠ total | INVALID_INVOICE_DATA  |
| Invalid UUID       | random-string       | 404 INVOICE_NOT_FOUND |
| Missing store name | storeName = null    | INVALID_INVOICE_DATA  |

---

## Data Privacy

- Invoice images downloaded from the internet contain no real
  personal data
- No real customer data or banking information was used
- AWS credentials used only via environment variables —
  never stored in test data or source code