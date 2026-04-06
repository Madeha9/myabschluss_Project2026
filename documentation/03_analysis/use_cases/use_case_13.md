# Use Case 13: View Invoice Metrics Summary

## Overview

**ID:** UC-13
**Name:** View Invoice Metrics Summary
**Primary Actor:** User
**Secondary Actors:** Quarkus Backend
**Brief Description:** The user sees a summary of their invoice
statistics at the top of the Invoice List page through
four metric cards.

## Preconditions

- User is on the Invoice List page
- Invoices are loaded from the backend

## Postconditions

- Four metric cards display current invoice statistics

## Main Success Scenario

1. User opens the Invoice List page
2. System loads all invoices from the backend
3. Angular calculates and displays:
    - Total invoices count
    - Returnable invoices count (green)
    - Invoices expiring within 5 days (orange)
    - Expired invoices count (red)

## Exception Flows

### Exception Flow 1: No Invoices

**Condition:** Database is empty

1. All metric cards display 0

## Frequency of Use

Automatic — displayed every time the Invoice List is opened