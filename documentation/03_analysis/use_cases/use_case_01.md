# Use Case 01: Submit Invoice (Upload or Camera Scan)

## Overview
**ID:** UC-01  
**Name:** Submit Invoice (Upload or Camera Scan)  
**Primary Actor:** User  
**Secondary Actors:** Cloud Storage Service, Backend API  
**Brief Description:** The user submits an invoice (image/PDF) by upload or camera scan so it can be stored in cloud storage and processed.

## Preconditions
- User has an invoice image (JPG/PNG) or PDF, or can scan it with the device camera
- System is available and cloud storage is reachable

## Postconditions
- Invoice file is stored in cloud storage
- File path/URL is stored in the database and an invoice ID is created

## Main Success Scenario
1. User selects an invoice file (image/PDF) or scans via camera.
2. System validates file type and size.
3. System uploads the file to cloud storage.
4. System stores the file path/URL and metadata in the database and returns the invoice ID.

## Alternative Flows

### Alternative Flow 1: Upload PDF
**Condition:** User submits a PDF invoice
1. User selects a PDF file.
2. System validates PDF format and size.
3. Continue at step 3 in main flow.

### Alternative Flow 2: Camera Scan
**Condition:** User scans invoice using device camera
1. User captures invoice image via camera.
2. Client submits captured image to backend.
3. Continue at step 2 in main flow.

## Exception Flows

### Exception Flow 1: Unsupported File Type
**Condition:** File is not JPG/PNG/PDF
1. System rejects the file.
2. System returns an error message.
3. Use case ends in failure.

### Exception Flow 2: File Too Large
**Condition:** File exceeds allowed size limit
1. System rejects the file.
2. System returns an error message.
3. Use case ends in failure.

### Exception Flow 3: Cloud Storage Error
**Condition:** Upload to cloud storage fails
1. System stops processing.
2. System returns an error message.
3. Use case ends in failure.

## Special Requirements
- Store only the cloud file location in the database (no binary storage in DB)
- Secure file upload and access (authenticated access if enabled)

## Frequency of Use
Frequent (multiple times per user, whenever a new invoice is submitted)

## Open Issues
- Decide maximum allowed file size and supported MIME(Multipurpose Internet Mail Extensions) types
- Decide cloud storage provider and access strategy (signed URLs vs public URLs)
