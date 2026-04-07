# Use Case 03: View Invoice List

## Overview

**ID:** UC-03
**Name:** View Invoice List
**Primary Actor:** User
**Secondary Actors:** Quarkus Backend, PostgreSQL
**Brief Description:** The user views all stored invoices in a table
with return status, days left, and image thumbnails.

## Use Case Diagram

![use_case_03.png](../Diagrams%26Images/use_case_diagrams/use_case_png/use_case_03.png)

## Preconditions

- At least one invoice is saved in the database
- The Quarkus backend is running

## Postconditions

- All invoices are displayed in the Angular UI with their current return status

## Main Success Scenario

1. User navigates to the Invoices page
2. System calls GET /myinvoices
3. Backend retrieves all invoices from PostgreSQL
4. Backend calculates return status and days left for each invoice
5. Angular UI displays invoices in a table with thumbnails,
   status badges, and action buttons

## Exception Flows

### Exception Flow 1: No Invoices Found

**Condition:** Database is empty

1. System returns an empty list
2. Angular UI displays "No invoices found"

### Exception Flow 2: Backend Unavailable

**Condition:** Quarkus backend is not running

1. Angular UI displays a connection error

## Frequency of Use

Very frequent — every time the user opens the application