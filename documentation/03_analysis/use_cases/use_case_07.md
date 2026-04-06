# Use Case 07: Filter Invoices by Return Status

## Overview

**ID:** UC-07
**Name:** Filter Invoices by Return Status
**Primary Actor:** User
**Secondary Actors:** None (handled entirely in Angular)
**Brief Description:** The user filters the invoice list
by return status using the filter chips.

## Preconditions

- User is on the Invoice List page
- Invoices are already loaded

## Postconditions

- Invoice table shows only invoices matching the selected status

## Main Success Scenario

1. User clicks a filter chip
   (Returnable, Non-returnable, or Satisfied)
2. Angular filters the already loaded invoice list
3. Table updates instantly with matching invoices

## Alternative Flows

### Alternative Flow 1: Multiple Filters Selected

**Condition:** User selects more than one filter chip

1. Angular shows invoices matching any of the selected statuses

## Exception Flows

### Exception Flow 1: No Matches

**Condition:** No invoices match the selected filter

1. Angular UI displays "No invoices found"

## Frequency of Use

Frequent — whenever user wants to check returnable items