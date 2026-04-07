# Use Case 08: View Spending Analytics

## Overview

**ID:** UC-08
**Name:** View Spending Analytics
**Primary Actor:** User
**Secondary Actors:** Quarkus Backend, PostgreSQL
**Brief Description:** The user views monthly and yearly spending
summaries through interactive Chart.js charts.

## Use Case Diagram

![use_case_08.png](../Diagrams%26Images/use_case_diagrams/use_case_png/use_case_08.png)

## Preconditions

- At least one invoice is saved in the database
- User is on the Analytics page

## Postconditions

- Spending charts are displayed for the selected period

## Main Success Scenario

1. User navigates to the Analytics page
2. System loads all invoices and calls
   GET /myinvoices/spending?year=&month=
3. Angular calculates and renders:
    - Monthly bar chart with colour per month
    - Quarterly donut chart
    - Top 5 stores horizontal bar chart
4. User selects a different year or month and clicks Apply
5. Charts update to reflect the selected period

## Exception Flows

### Exception Flow 1: No Data for Period

**Condition:** No invoices exist for the selected year/month

1. Charts display empty or zero values
2. Metric cards show € 0.00

## Frequency of Use

Occasional — when user wants to review spending patterns