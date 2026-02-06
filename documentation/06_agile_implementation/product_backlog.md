# Product Backlog

## Overview
This document contains the product backlog - the prioritized list of features, enhancements, and fixes for the product.

## Product Vision
The vision of this product is to reduce the reliance on paper invoices by providing a secure and user-friendly
digital invoice system. Customers can store and manage their purchase invoices digitally,  ensuring they are always 
available for returns or exchanges even if the paper receipt is lost or damaged. In addition, the system aims to support
customers during the shopping experience through an AI-based assistant that provides product suggestions for clothing and
shoes, such as color and style matching. The system also enables users to analyze their monthly shopping activity and
document their experiences across different physical retail stores.

## Product Backlog Items

### Epic 1: Invoice Upload and Storage
**Description:** Users can scan invoices using their device camera or upload existing images or PDF files from the gallery. 
The system stores the invoices digitally and extracts relevant invoice data, such as QR codes and purchase information, 
which is then saved in the database.
**Business Value:** High
**Status:** Not Started

#### User Stories

##### PBI-001: Scan Invoice with Camera
**As a** user
**I want**  to scan an invoice using my phone camera
**So that** I can save it digitally in the system

**Acceptance Criteria:**
- [ ] The user is able to access the device camera and capture an invoice image
- [ ] The system successfully stores the captured invoice within the application
- [ ] The user can crop the captured image and select a specific section of the invoice prior to saving
- [ ]The system allows the user to activate the device camera

**Priority:** High
**Estimation:** 5
**Status:** Backlog
**Sprint:** [Sprint number or N/A]
**Notes:** Image quality may affect scan accuracy

##### PBI-002: Upload Invoice from Device Gallery
**As a** user 
**I want** to upload invoice images from my device gallery
**So that** I can store them securely in the system and access them anytime from any device

**Acceptance Criteria:**
1. [ ] The system allows the user to access the device’s image gallery
2. [ ] The system allows the user to select one or multiple images from the gallery
3. [ ] The system accepts only valid invoice images
4. [ ] The system prevents the upload of non-invoice images (e.g., personal photos)
5. [ ] All selected images are uploaded successfully
6. [ ] Each uploaded invoice is stored securely in the system and linked to the user account

**Priority:** High
**Estimation:** 5
**Status:** Backlog
**Sprint:** [Sprint number or N/A]

##### PBI-003: Upload Invoices as PDF Documents
**As a** user 
**I want** to upload invoice documents in PDF format from my device
**So that** I can store them securely in the system and access them when needed

**Acceptance Criteria:**

- [ ] The system allows the user to access documents on the device
- [ ] The system allows the user to select one or multiple PDF files
- [ ] The system accepts only files in PDF format
- [ ] The system validates that the PDF contains invoice-related information (e.g., prices in EUR, invoice metadata)
- [ ] Valid PDF invoices are uploaded successfully
- [ ] Each uploaded PDF invoice is stored securely in the system and linked to the user account


**Priority:** Medium
**Estimation:** 4
**Status:** Backlog
**Sprint:** [Sprint number or N/A]

##### PBI-004: Extract Invoice Data
**As a** user
**I want** the system to extract relevant information from invoices
**So that** the invoice content can be processed and reviewed

**Acceptance Criteria:**

- [ ] The system extracts invoice-related information from uploaded images or PDF documents
- [ ] The system identifies key fields such as product details, price, VAT, and store information
- [ ] The system attempts data extraction even if the invoice quality is low (prototype level)
- [ ] The system handles incomplete or unclear data without failing the extraction process
- [ ] The extracted data is made available for further processing


**Priority:** High
**Estimation:** 5
**Status:** Backlog
**Sprint:** [Sprint number or N/A]

##### PBI-005: Store Extracted Invoice Data
**As a** user 
**I want** extracted invoice data to be stored in structured database tables
**So that** I can search, filter, and track my shopping history

**Acceptance Criteria:**

- [ ] The system receives extracted invoice data from the extraction process
- [ ] The system validates extracted data before storage
- [ ] The system stores invoice data in structured database tables
- [ ] The stored data is linked to the corresponding invoice and user account
- [ ] The system allows stored invoice data to be retrieved for later use


**Priority:** High
**Estimation:** 5
**Status:** Backlog
**Sprint:** [Sprint number or N/A]


### Epic 2: [Epic Name]
**Description:** [Epic description]
**Business Value:** [High/Medium/Low]
**Status:** [Not Started/In Progress/Done]


#### User Stories

##### PBI-006: [User Story Title]
**As a** [user role]
**I want** [goal]
**So that** [benefit]

**Acceptance Criteria:**
- [ ] Criterion 1
- [ ] Criterion 2
- [ ] Criterion 3

**Priority:** [High/Medium/Low]
**Estimation:** [Story points]
**Status:** [Backlog/Ready/In Progress/Done]
**Sprint:** [Sprint number or N/A]

##### PBI-007: [User Story Title]
[User story details]

##### PBI-004: [User Story Title]
[User story details]

## Technical Debt Items

### TD-001: [Technical Debt Item]
**Description:** [Description]
**Impact:** [High/Medium/Low]
**Effort:** [Story points]
**Priority:** [High/Medium/Low]
**Status:** [Backlog/Ready/In Progress/Done]

## Bugs

### BUG-001: [Bug Title]
**Description:** [Bug description]
**Severity:** [Critical/High/Medium/Low]
**Priority:** [High/Medium/Low]
**Status:** [Open/In Progress/Fixed/Closed]
**Found in:** [Sprint/Version]

## Backlog Refinement

### Last Refinement Date
[06.02.2026]

### Next Refinement Date
[Date]

### Refinement Notes
- [Note 1]
- [Note 2]

## Backlog Metrics

| Metric | Value |
|--------|-------|
| Total PBIs | 0 |
| Ready for Sprint | 0 |
| In Progress | 0 |
| Done | 0 |
| Total Story Points | 0 |
| Velocity (last 3 sprints) | 0 |

## Prioritization Criteria
1. [Criterion 1]
2. [Criterion 2]
3. [Criterion 3]
