# Use Case 02: Extract Invoice Data with LLM

## Overview
**ID:** UC-02  
**Name:** Extract Invoice Data with LLM  
**Primary Actor:** User  
**Secondary Actors:** LLM Service, Backend API  
**Brief Description:** The system automatically extracts structured invoice data from a submitted invoice using an LLM.

## Preconditions
- Invoice file has been successfully uploaded
- Invoice file path is stored in the database

## Postconditions
- Structured invoice data is stored in the database
- Return eligibility and return deadline are calculated

## Main Success Scenario
1. System retrieves the stored invoice file.
2. System sends invoice content to the LLM with a structured prompt.
3. LLM returns structured JSON data.
4. System validates the response format.
5. System stores extracted fields in the database.

## Alternative Flows

### Alternative Flow 1: Partial Data Extraction
**Condition:** Some optional fields are missing
1. System stores available fields.
2. Missing optional fields are stored as null.
3. Use case continues successfully.

## Exception Flows

### Exception Flow 1: LLM Failure
**Condition:** LLM does not return valid JSON
1. System logs the error.
2. System returns an error message.
3. Use case ends in failure.

## Special Requirements
- Strict JSON validation
- Configurable LLM prompt

## Frequency of Use
Frequent (triggered after each invoice submission)

## Open Issues
- Define exact JSON schema for extraction
- Define timeout strategy for LLM response
