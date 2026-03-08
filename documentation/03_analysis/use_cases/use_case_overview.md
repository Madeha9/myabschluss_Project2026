# Use Case Overview

## Introduction
This document provides an overview of the main use cases of the Invoice Management System.  
The system allows users to submit invoices, extract structured data using an LLM, store invoices,
and view return reminders.

## Use Case Diagram

![img.png](Use_Case_Diagram%20_01.png)
![img.png](Use_Case_Diagram%20_02.png)

## Use Case List

| ID    | Use Case Name            | Primary Actor | Priority | Status |
|-------|--------------------------|---------------|----------|--------|
| UC-01 | Submit Invoice           | User          | High     | Draft  |
| UC-02 | Extract Invoice Data     | User          | High     | Draft  |
| UC-03 | View and Manage Invoices | User          | High     | Draft  |
| UC-04 | View Return Reminders    | User          | Medium   | Draft  |

## Actors

### User
**Description:** A person who uses the system to manage digital invoices.  
**Responsibilities:**
- Upload or scan invoices
- View stored invoices
- Check return deadlines
- Delete invoices

### External Services
**Description:** External systems that support the application.  
**Responsibilities:**
- Cloud Storage Service stores uploaded invoice files
- LLM Service extracts structured invoice data

## System Boundaries
The system includes the backend API, database, and integration with cloud storage and LLM services.  
The user interacts with the system through a client application (e.g., web interface).  
External services such as cloud storage and the LLM are outside the system boundary.
