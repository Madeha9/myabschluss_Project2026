# Use Case 09: View Original Invoice Image

## Overview

**ID:** UC-09
**Name:** View Original Invoice Image
**Primary Actor:** User
**Secondary Actors:** AWS S3
**Brief Description:** The user clicks an invoice thumbnail
to view the original invoice image stored in AWS S3.

## Use Case Diagram

![use_cse_09.png](../Diagrams%26Images/use_case_diagrams/use_case_png/use_cse_09.png)

## Preconditions

- Invoice has an imageUrl stored in the database
- AWS S3 is accessible

## Postconditions

- Original invoice image is displayed in a modal or new browser tab

## Main Success Scenario

1. User clicks the image thumbnail in the invoice table
   or detail page
2. Angular UI opens a modal showing the full invoice image
   loaded from the AWS S3 URL
3. User can view the S3 URL and click "Open original"
   to open the image in a new browser tab

## Exception Flows

### Exception Flow 1: Image Not Available

**Condition:** S3 URL is broken or image was deleted from S3

1. Image fails to load
2. Angular UI hides the broken image element

## Frequency of Use

Occasional — when user needs to verify the original receipt