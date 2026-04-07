# Use Case 04: Search Invoice by Store Name

## Overview

**ID:** UC-04
**Name:** Search Invoice by Store Name
**Primary Actor:** User
**Secondary Actors:** Quarkus Backend, PostgreSQL
**Brief Description:** The user searches for invoices by typing
a store name in the search field.

## Use Case Diagram

![use_case_04.png](../Diagrams%26Images/use_case_diagrams/use_case_png/use_case_04.png)

## Preconditions

- At least one invoice is saved in the database
- User is on the Invoice List page

## Postconditions

- Filtered invoice list is displayed matching the search term

## Main Success Scenario

1. User types a store name in the search field
2. System calls GET /myinvoices/search?name=
3. Backend filters invoices by store name (case insensitive)
4. Angular UI updates the table with matching results

## Exception Flows

### Exception Flow 1: No Results Found

**Condition:** No invoice matches the search term

1. Angular UI displays "No invoices found"

## Frequency of Use

Occasional — when user is looking for a specific store