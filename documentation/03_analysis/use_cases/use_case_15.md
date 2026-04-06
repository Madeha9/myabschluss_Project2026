# Use Case 15: View Quarterly Spending Breakdown

## Overview

**ID:** UC-15
**Name:** View Quarterly Spending Breakdown
**Primary Actor:** User
**Secondary Actors:** None (calculated in Angular)
**Brief Description:** The user views their spending broken down
by quarter (Q1–Q4) in a donut chart on the Analytics page.

## Preconditions

- At least one invoice exists for the selected year
- User is on the Analytics page

## Postconditions

- Donut chart displays spending split across four quarters

## Main Success Scenario

1. User is on the Analytics page
2. Angular groups invoice totals by quarter:
    - Q1: January — March
    - Q2: April — June
    - Q3: July — September
    - Q4: October — December
3. Donut chart renders each quarter with a distinct colour
4. User hovers over a segment to see the exact amount

## Exception Flows

### Exception Flow 1: No Data for Some Quarters

**Condition:** User has invoices in only some quarters

1. Empty quarters display as zero in the chart
2. Donut chart shows only the quarters with data

## Frequency of Use

Occasional — when user wants a quarterly spending overview