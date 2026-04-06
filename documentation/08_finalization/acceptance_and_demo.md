# Acceptance and Demo

## Demo Information

**Date:** 2026-04-10
**Location:** WiFi Vienna
**Presenter:** Madeha Mohammed
**Audience:** Course trainer and evaluators

---

## Demo Agenda

| # | Topic                                               | Time  |
|---|-----------------------------------------------------|-------|
| 1 | Project introduction and architecture overview      | 5 min |
| 2 | Upload invoice — AI extraction live demo            | 5 min |
| 3 | Invoice list — search, filter, thumbnails           | 5 min |
| 4 | Invoice detail — return deadline and original image | 5 min |
| 5 | Spending analytics — charts and filters             | 5 min |
| 6 | Q&A                                                 | 5 min |

**Total: ~30 minutes**

---

## Demo Script

### Step 1 — Upload Invoice

1. Navigate to the Upload page
2. Drag and drop a real invoice image (JPG)
3. Click "Process invoice"
4. Show AI extracting store name, date, total, VAT, line items
5. Confirm and save
6. **Key point:** Zero manual data entry — Claude Sonnet does everything

### Step 2 — Invoice List

1. Navigate to Invoice List
2. Show metric cards (total, returnable, expiring, expired)
3. Search by store name
4. Filter by return status
5. Click image thumbnail to show original invoice from AWS S3
6. **Key point:** Return deadlines calculated automatically

### Step 3 — Invoice Detail

1. Click View on any invoice
2. Show all extracted fields and line items
3. Show return window progress bar and days remaining
4. Show original invoice image loaded from AWS S3
5. **Key point:** Full data traceability from image to structured data

### Step 4 — Analytics

1. Navigate to Analytics page
2. Show monthly bar chart with colour per month
3. Show quarterly donut chart
4. Show top stores horizontal bar chart
5. Change year/month filter and click Apply
6. **Key point:** All charts built from real invoice data

---

## Pre-Demo Checklist

- [ ] Quarkus backend running on http://localhost:8080
- [ ] Angular frontend running on http://localhost:4200
- [ ] AWS credentials set in IntelliJ environment variables
- [ ] At least 5 real invoice images ready for upload
- [ ] PostgreSQL running and connected
- [ ] Browser console closed and screen resolution set

---

## Acceptance Criteria Review

| Requirement                            | Status | Evidence                       |
|----------------------------------------|--------|--------------------------------|
| User can upload invoice image or PDF   | Met    | Live demo                      |
| AI extracts invoice data automatically | Met    | Live demo                      |
| Math validation ensures data accuracy  | Met    | Error shown on invalid invoice |
| Return deadlines tracked automatically | Met    | Days left shown in list        |
| Monthly spending charts displayed      | Met    | Analytics page                 |
| Invoices can be searched and filtered  | Met    | Live demo                      |
| Original image stored and accessible   | Met    | AWS S3 URL in detail page      |

---

## Acceptance Decision

**Status:** Pending
**Decision Maker:** WiFi Vienna Trainers
**Date:** TBD

*To be completed by the evaluating trainer after the demo presentation.*

---

## Frontend Note

The Angular frontend was generated with the assistance of Claude AI
and serves as a visual interface to demonstrate the backend.
The core project work is the Java/Quarkus backend including REST API,
Claude Sonnet AI integration, AWS S3 storage, math validation,
and return deadline tracking.