# Sprint 01

## Sprint Information

**Sprint Number:** 01
**Sprint Goal:** Set up the project structure and implement
the core invoice upload flow with AWS S3 storage
**Start Date:** 2026-02-03
**End Date:** 2026-02-13
**Duration:** 10 days
**Developer:** Madeha Mohammed

---

## Sprint Backlog

| ID      | User Story                                      | Points | Status |
|---------|-------------------------------------------------|--------|--------|
| PBI-001 | Upload invoice image or PDF via drag-and-drop   | 8      | Done   |
| PBI-002 | AI extracts store, date, total, VAT, line items | 8      | Done   |
| PBI-003 | Confirm extracted data before saving            | 3      | Done   |

**Total committed: 19 story points**

---

## Tasks

| Task                                                          | Estimated | Status |
|---------------------------------------------------------------|-----------|--------|
| Set up Maven multi-module project (parent, backend, frontend) | 4h        | Done   |
| Create InvoiceEntity, InvoiceItemEntity, InvoiceRepository    | 4h        | Done   |
| Integrate AWS S3 via CloudStorageService                      | 4h        | Done   |

**Total estimated: 40h**

---

## Sprint Capacity

| Member          | Availability | Available Hours | Committed Hours |
|-----------------|--------------|-----------------|-----------------|
| Madeha Mohammed | 100%         | 40h             | 40h             |

---

## Sprint Review

**Date:** 2026-02-13

### Demonstrated Items

| PBI     | Description                          | Result   |
|---------|--------------------------------------|----------|
| PBI-001 | Upload JPG invoice to AWS S3         | Accepted |
| PBI-002 | Claude Sonnet extracts invoice data  | Accepted |

### Metrics

| Metric                 | Value |
|------------------------|-------|
| Planned story points   | 19    |
| Completed story points | 19    |
| Sprint goal achieved   | Yes   |

---

## Sprint Retrospective

### What went well

- LangChain4j integration with Claude Sonnet was straightforward
- AWS S3 upload and URL storage worked on first attempt

### What could be improved

- AI extraction fails on low quality images — need better
  error handling
- Math validation edge cases took longer than expected

### Action items for Sprint 02

- [ ] Add better error messages for AI extraction failures
- [ ] Start invoice list and detail pages in Angular
- [ ] Add return status calculation to invoice detail