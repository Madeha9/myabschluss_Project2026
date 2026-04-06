# Use Case 14: View Top Stores in Analytics

## Overview

**ID:** UC-14
**Name:** View Top Stores in Analytics
**Primary Actor:** User
**Secondary Actors:** None (calculated in Angular)
**Brief Description:** The user views the top 5 stores ranked
by total spending in the Analytics page.

## Preconditions

- At least one invoice exists in the database
- User is on the Analytics page

## Postconditions

- Top 5 stores are displayed ranked by total spend
  in a horizontal bar chart

## Main Success Scenario

1. User navigates to the Analytics page
2. Angular groups all invoices by store name
3. Angular calculates total spending per store
4. Top 5 stores are sorted by total spend descending
5. Horizontal bar chart displays each store with
   its own colour and total amount

## Exception Flows

### Exception Flow 1: Less Than 5 Stores

**Condition:** User has invoices from fewer than 5 stores

1. Chart displays only the available stores

## Frequency of Use

Occasional — when user reviews spending by store