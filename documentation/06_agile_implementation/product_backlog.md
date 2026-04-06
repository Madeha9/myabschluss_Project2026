# Product Backlog

## Product Vision

To eliminate paper receipts by providing an AI-powered digital invoice
management system — users upload a photo, Claude Sonnet extracts all
data automatically, and return deadlines are tracked in real time.

---

## Implemented Features

| ID      | User Story                                      | Priority | Points | Status |
|---------|-------------------------------------------------|----------|--------|--------|
| PBI-001 | Upload invoice image or PDF via drag-and-drop   | High     | 8      | Done   |
| PBI-002 | AI extracts store, date, total, VAT, line items | High     | 8      | Done   |
| PBI-003 | Confirm extracted data before saving            | High     | 3      | Done   |
| PBI-004 | View invoice list with search and status filter | High     | 5      | Done   |
| PBI-005 | View invoice detail with original image from S3 | High     | 3      | Done   |
| PBI-006 | Delete invoice with confirmation                | Medium   | 2      | Done   |
| PBI-007 | Track return deadlines automatically            | High     | 5      | Done   |
| PBI-008 | View spending analytics with Chart.js charts    | Medium   | 5      | Done   |
| PBI-009 | View original invoice image thumbnail in list   | Medium   | 2      | Done   |
| PBI-010 | Filter invoices by return status (chips)        | Medium   | 2      | Done   |
| PBI-011 | Search invoices by store name                   | Medium   | 2      | Done   |
| PBI-012 | View metric summary cards on invoice list       | Low      | 1      | Done   |

**Total delivered: 46 story points**

---

## Future Roadmap

| ID      | User Story                             | Priority | Points |
|---------|----------------------------------------|----------|--------|
| PBI-013 | Camera capture directly in the browser | Medium   | 8      |
| PBI-014 | JWT authentication and user accounts   | High     | 8      |
| PBI-015 | AI purchase recommendations            | Low      | 13     |

**Total remaining: 29 story points**

---

## Technical Debt

| ID     | Description                                     | Priority |
|--------|-------------------------------------------------|----------|
| TD-001 | File size limit not enforced on frontend        | Medium   |
| TD-002 | Camera capture not supported in current version | Medium   |
| TD-003 | No JWT authentication implemented               | High     |
| TD-004 | Flyway not used for database migrations         | Low      |