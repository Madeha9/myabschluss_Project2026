# GUI Sketches

## Overview

This document describes the main user interface views of the IntelliInvoice system.
The UI is built with Angular 21 and Angular Material using a pink M3 theme.
It allows users to upload invoices, view and manage stored invoices, track return
deadlines, and analyse spending patterns through interactive charts.

## Design Principles

- **Simplicity** — Clean and minimal interface focused on core features
- **Consistency** — Uniform pink theme and Angular Material components across all views
- **Usability** — Easy invoice upload with step-by-step guidance and clear data presentation
- **Feedback** — Every user action receives a visible response (success, error, loading state)

---

## Main Views

### View 1: Invoice List

**Purpose:** Displays all processed invoices stored in the database with
filtering, search, and return status tracking
**User:** Single system user

![Invoice_List_View .png](Diagrams%26Images/gui_readme%26sketches/Invoice_List_View%20.png)

**Components:**

- Summary metric cards (total invoices, returnable, expiring soon, expired)
- Search field — filter invoices by store name
- Filter chips — filter by status (Returnable, Non-returnable, Satisfied)
- Invoice table with columns:
    - Image thumbnail (clickable — opens original invoice from AWS S3)
    - Invoice number
    - Store name
    - Invoice date
    - Total amount and currency
    - VAT amount
    - Return status badge (colour coded)
    - Days left to return
    - View and Delete action buttons
- Empty state message when no invoices exist
- Image modal — shows full invoice image and S3 URL when thumbnail is clicked

**Interactions:**

- User searches invoices by store name
- User filters invoices by return status
- User clicks thumbnail to view original invoice image from AWS S3
- User clicks View to navigate to invoice detail page
- User clicks Delete to remove an invoice with confirmation dialog

---

### View 2: Upload Invoice

**Purpose:** Allows the user to upload a retail invoice image or PDF
for AI processing and storage
**User:** Single system user

![Upload_View.png](Diagrams%26Images/gui_readme%26sketches/Upload_View.png)

**Components:**

- Step indicator (3 steps: Select file → Review → AI processing)
- Drag-and-drop upload zone
- File browser button
- File preview (name, size, type)
- Information banner explaining AI extraction
- Process Invoice button
- Loading spinner during AI processing
- Extracted data confirmation screen
- Success screen with summary and navigation options
- Error screen with specific error message

**Interactions:**

- User drags and drops or selects an invoice file (JPG, PNG, PDF)
- User reviews the selected file before processing
- System uploads file to AWS S3
- System sends image to Claude Sonnet AI for data extraction
- System displays extracted data (store, date, total, VAT, invoice number, status)
- User confirms and saves or discards the result
- On success: user can upload another or navigate to invoice list

---

### View 3: Invoice Detail

**Purpose:** Shows full extracted invoice data for a single invoice
including line items, return window progress, and original image
**User:** Single system user

![Invoice_Details.png](Diagrams%26Images/gui_readme%26sketches/Invoice_Details.png)

**Components:**

- Back button to invoice list
- Invoice details card:
    - Store name and invoice number
    - Invoice date, total amount, VAT, currency
    - Return status badge
    - Days remaining
    - Return window progress bar
- Original invoice image card:
    - Image loaded from AWS S3 URL
    - Clickable link to open full image
    - S3 URL displayed
- Line items table:
    - Description, quantity, unit price, line total
- Delete button

**Interactions:**

- User reviews all extracted invoice data
- User views the original invoice image from AWS S3
- User opens the original image in a new browser tab
- User navigates back to the invoice list
- User deletes the invoice with confirmation

---

### View 4: Spending Analytics

**Purpose:** Provides visual spending insights based on stored invoices
**User:** Single system user

![Analytics .png](Diagrams%26Images/gui_readme%26sketches/Analytics%20.png)

**Components:**

- Year and month selector filters
- Apply button
- Summary metric cards (total spend, invoices in period, average per invoice)
- Monthly spending bar chart — each month has its own colour
- Quarterly spending donut chart (Q1–Q4)
- Top stores horizontal bar chart (top 5 stores by spend)
- Colour-coded legend for monthly chart

**Interactions:**

- User selects year and month and clicks Apply
- Charts update to reflect the selected period
- User hovers over chart bars to see exact spending amounts
- User identifies highest spending months and top stores

---

### View 5: Sidebar Navigation

**Purpose:** Provides consistent navigation across all views
**User:** Single system user

![Upload_View.png](Diagrams%26Images/gui_readme%26sketches/Upload_View.png)

**Components:**

- IntelliInvoice logo and app name
- Navigation links: Invoices, Upload, Analytics
- Active link highlight in pink
- Icons for each navigation item

**Interactions:**

- User clicks a navigation item to switch between views
- Active page is visually highlighted

---

## Navigation Flow

```
Upload Invoice
       |
       ↓
    Invoice List  ───────────→  Invoice Detail
       |                              |
       |←─────────────────────────────|
       ↓
   Analytics
```

**Upload** → **Invoice List** → **Invoice Detail** → *(back)* → **Invoice List** → **Analytics**

---

## Design System

| Element                | Value                                 |
|------------------------|---------------------------------------|
| Framework              | Angular Material 21 — M3 theme        |
| Primary colour         | Pink — `#880e4f`                      |
| Background             | Soft pink — `#fdf5f8`                 |
| Table header           | Light pink — `#fce4ec`                |
| Status: Returnable     | Green badge                           |
| Status: Non-returnable | Pink badge                            |
| Status: Satisfied      | Purple badge                          |
| Font                   | Roboto                                |
| Charts                 | Chart.js with per-month colour coding |

---

## Responsive Design Considerations

- Layout adapts to desktop and tablet screens
- Tables become horizontally scrollable on smaller screens
- Metric card grid collapses to two columns on narrow screens

## Accessibility Requirements

- Clear labels for all input fields and buttons
- Colour-coded status badges always include a text label
- Meaningful error and success messages for every user action
- Loading states are shown during API calls to indicate system activity

## Known Limitations

- No direct camera capture — users must upload an existing file from their device
- Invoice image thumbnails require imageUrl to be returned by the backend API
- The application is optimised for desktop browsers —
  mobile layout is functional but not fully optimised