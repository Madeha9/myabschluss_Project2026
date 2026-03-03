# GUI Sketches

## Overview
This document describes the main user interface views of the IntelliInvoice system.
The UI is designed to allow users to upload invoices, view stored invoices, and see extracted invoice details.
## Design Principles
- Simplicity – Clean and minimal interface focused on core features
- Consistency – Uniform layout and button styles across all views
- Usability – Easy invoice upload and clear data presentation

## Main Views

### View 1: Upload Invoice
**Purpose:** Allows the user to upload a retail invoice image for processing
**User:**  Single system user

![Upload View.png](gui-images/Upload%20View.png)


**Components:**
- File upload button
- Submit button
- Status message display

**Interactions:**
- User selects an invoice image
- System stores the image in cloud storage
- System processes the image using the LLM
- System saves extracted data in the database
- System shows success or error message

### View 2: Invoice List
**Purpose:** Displays all processed invoices stored in the database
**User:** Single system user

![Invoice_List_View .png](gui-images/Invoice_List_View%20.png)


**Components:**
- Invoice table
- View details button
- Delete button (optional)

**Interactions:**
- User clicks "Open" to view invoice details.
- User can delete an invoice.


### View 3: Invoice Details
**Purpose:** Shows extracted structured invoice data
**User:** Single system user

![Invoice_Details_View .png](gui-images/Invoice_Details_View%20.png)

**Components:**
- Structured invoice data display
- Back button

  **Interactions:**
- User reviews extracted data.
- User navigates back to invoice list.

## Navigation Flow
User uploads invoice → System processes invoice →  User is redirected to Invoice List →  User opens Invoice Details → User returns to Invoice List.

## Responsive Design Considerations
- Layout adapts to desktop and tablet screens
- Tables become scrollable on smaller screens
- Buttons stack vertically on mobile devices


## Accessibility Requirements
- Clear labels for all input fields
- High contrast between text and background
- Meaningful error and success messages
