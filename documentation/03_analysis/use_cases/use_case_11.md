# Use Case 11: Navigate Between Pages

## Overview

**ID:** UC-11
**Name:** Navigate Between Pages
**Primary Actor:** User
**Secondary Actors:** None
**Brief Description:** The user navigates between the main pages
of the application using the sidebar navigation.

## Preconditions

- Angular application is running
- User is on any page of the application

## Postconditions

- User is redirected to the selected page

## Main Success Scenario

1. User clicks a navigation item in the sidebar
   (Invoices, Upload, or Analytics)
2. Angular Router navigates to the selected page
3. The active link is highlighted in pink in the sidebar
4. The selected page content is displayed

## Exception Flows

### Exception Flow 1: Page Not Found

**Condition:** User navigates to an unknown route

1. Angular redirects to the Invoice List page by default

## Frequency of Use

Very frequent — every time the user switches between pages