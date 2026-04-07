# Use Case 12: Review Extracted Invoice Data

## Overview

**ID:** UC-12
**Name:** Review Extracted Invoice Data
**Primary Actor:** User
**Secondary Actors:** Quarkus Backend, PostgreSQL
**Brief Description:** After AI extraction the invoice is
automatically saved to the database. The user reviews the
extracted data in the detail page and deletes it if incorrect.

## Use Case Diagram

![use_case_12.png](../Diagrams%26Images/use_case_diagrams/use_case_png/use_case_12.png)

## Preconditions

- UC-02 completed successfully
- Invoice automatically saved to PostgreSQL
- Invoice visible in the Invoice List

## Postconditions

- Data correct → invoice stays in the database
- Data wrong → user deletes and re-uploads

## Main Success Scenario

1. System automatically saves invoice to PostgreSQL
2. User opens the Invoice List
3. User clicks View to open the invoice detail
4. User reviews all extracted fields and line items
5. Data is correct — invoice is kept

## Alternative Flow: Data is Incorrect

1. User finds wrong data in the detail page
2. User clicks Delete
3. System removes invoice and all line items
4. User re-uploads with a better quality image

## Special Requirements

- No manual confirm step — data is saved automatically
- User cannot edit fields — delete and re-upload only

## Frequency of Use

Every time an invoice is successfully processed by the AI