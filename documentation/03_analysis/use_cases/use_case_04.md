# Use Case 04: View Return Reminders

## Overview
**ID:** UC-04  
**Name:** View Return Reminders  
**Primary Actor:** User  
**Secondary Actors:** Backend API  
**Brief Description:** The user views invoices that are close to their return deadline.

## Preconditions
- Invoices contain invoice_date and return_window_days
- Return deadline has been calculated

## Postconditions
- User receives a list of invoices expiring within a defined time range

## Main Success Scenario
1. User requests invoices nearing return deadline.
2. System calculates remaining days until return deadline.
3. System filters invoices within specified time frame (e.g., 7 days).
4. System returns sorted list by nearest deadline.

## Alternative Flows

### Alternative Flow 1: No Expiring Invoices
**Condition:** No invoices meet criteria
1. System returns empty list.
2. Use case ends successfully.

## Exception Flows

### Exception Flow 1: Missing Return Data
**Condition:** Invoice lacks return policy information
1. System excludes invoice from reminder list.
2. Use case continues.

## Special Requirements
- Accurate date calculation
- Efficient filtering logic

## Frequency of Use
Moderate (users check before return deadlines)

## Open Issues
- Default reminder time frame (e.g., 7 days?)
- Future extension: automatic email reminders
