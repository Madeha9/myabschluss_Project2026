# Use Case Overview

## Introduction

This document provides an overview of all use cases of the IntelliInvoice system.
The system allows users to upload invoice images, automatically extract structured
data using Claude Sonnet AI, store invoices in the cloud, track return deadlines,
and analyse spending patterns through an Angular web interface.

## Use Case Diagram

![use_case_overview.png](../Diagrams%26Images/use_case_diagrams/use_case_png/use_case_overview.png)

## Use Case List

| ID    | Use Case Name                        | Primary Actor | Priority | Status      |
|-------|--------------------------------------|---------------|----------|-------------|
| UC-01 | Upload Invoice                       | User          | High     | Implemented |
| UC-02 | Extract Invoice Data with AI         | System        | High     | Implemented |
| UC-03 | View Invoice List                    | User          | High     | Implemented |
| UC-04 | Search Invoice by Store Name         | User          | Medium   | Implemented |
| UC-05 | View Invoice Detail                  | User          | High     | Implemented |
| UC-06 | Delete Invoice                       | User          | Medium   | Implemented |
| UC-07 | Filter Invoices by Return Status     | User          | Medium   | Implemented |
| UC-08 | View Spending Analytics              | User          | Medium   | Implemented |
| UC-09 | View Original Invoice Image          | User          | Low      | Implemented |
| UC-10 | View Return Deadline Warning         | System        | High     | Implemented |
| UC-11 | Navigate Between Pages               | User          | Medium   | Implemented |
| UC-12 | Confirm Extracted Data Before Saving | User          | High     | Implemented |
| UC-13 | View Invoice Metrics Summary         | System        | Medium   | Implemented |
| UC-14 | View Top Stores in Analytics         | System        | Low      | Implemented |
| UC-15 | View Quarterly Spending Breakdown    | System        | Low      | Implemented |

## Actors

### User

A person who uses the system to manage their personal invoices.

**Responsibilities:**

- Upload invoice images or PDFs
- View, search, and filter stored invoices
- Check return deadlines and invoice details
- Delete invoices and review spending analytics

### Claude Sonnet AI

The Anthropic AI model used for automatic invoice data extraction.

**Responsibilities:**

- Read invoice images from AWS S3
- Extract structured data and return it as a Java object

### AWS S3

Cloud storage service for storing original invoice images.

**Responsibilities:**

- Store uploaded invoice files
- Provide image URLs for display in the UI

### PostgreSQL

The relational database storing all invoice data.

**Responsibilities:**

- Persist invoice and line item data
- Support retrieval, search, and deletion

## System Boundaries

The IntelliInvoice system includes the Quarkus backend API, PostgreSQL database,
and Angular frontend. External services — AWS S3 and the Anthropic Claude API —
are outside the system boundary but are required for core functionality.
The user interacts with the system through a browser-based web interface.