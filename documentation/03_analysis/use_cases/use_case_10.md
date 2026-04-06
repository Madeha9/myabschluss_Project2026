# Use Case 10: View Return Deadline Warning

## Overview

**ID:** UC-10
**Name:** View Return Deadline Warning
**Primary Actor:** User
**Secondary Actors:** Quarkus Backend
**Brief Description:** The system automatically highlights invoices
that are expiring within 5 days so the user can act before
the return window closes.

## Preconditions

- At least one RETURNABLE invoice exists
- User is on the Invoice List page

## Postconditions

- Invoices expiring within 5 days are visually highlighted
  with an orange warning badge and days remaining

## Main Success Scenario

1. User opens the Invoice List page
2. System calculates daysLeft for each invoice
3. Invoices with daysLeft between 1 and 5 are shown
   with an orange "Expiring soon" badge
4. The metric card "Expiring in 5 days" shows the count
5. User can click View to open the invoice detail
   before the deadline passes

## Exception Flows

### Exception Flow 1: No Expiring Invoices

**Condition:** No invoices are expiring within 5 days

1. Expiring metric card shows 0
2. No orange badges are displayed

## Frequency of Use

Automatic — shown every time the invoice list is loaded