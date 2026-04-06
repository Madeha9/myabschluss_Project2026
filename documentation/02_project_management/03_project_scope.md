# Project Scope

IntelliInvoice focuses on the post-purchase experience — from uploading
a receipt to tracking return deadlines and understanding spending patterns.
The following defines what is included and excluded from the current version.

## In Scope

The project includes the following features and functionality:

### Invoice Management

- Upload, store, and view invoices (JPG, PNG, PDF)
- AI-powered extraction of store name, date, total, VAT,
  invoice number, and line items via Claude Sonnet
- Automatic math validation before saving
- Secure image storage in AWS S3

### Return Tracking

- Automatic calculation of days remaining to return each item
- Invoice status: RETURNABLE, NON_RETURNABLE, SATISFIED
- Search and filter invoices by store name and return status

### Spending Analytics

- Monthly and yearly spending summaries
- Colour-coded bar chart per month
- Quarterly spending breakdown
- Top stores ranked by total spend

### Future / Prototype Features

- AI-assisted return eligibility suggestions
- Decision support based on purchase history

## Out of Scope

The following functionality is explicitly excluded from the project:

- Online shopping or e-commerce functionality
- Online payment processing or access to bank or credit card information
- Integration with real retail systems or point-of-sale systems
- Real-time product availability or pricing information
- Fully autonomous or production-ready AI agents
- Legal validation of invoices or guarantees of return acceptance by retailers
- Native mobile application — the system is designed and delivered
  as a web application accessible via browser
- Cloud deployment for production — cloud features (AWS S3, Anthropic API)
  are used for demonstration purposes within the academic scope
- Direct camera access — users cannot take a photo of a receipt
  directly within the application. Invoice images must be uploaded
  as existing files from the device.

## Constraints

The project is subject to the following constraints:

- **Time constraint:**
  The system is developed within a limited timeframe as part of a final
  student project at WIFI Vienna.

- **Technical constraint:**
  AI extraction depends on image quality — blurry or unclear photos may
  reduce accuracy. AI features are implemented at prototype level and are
  not production-ready.

- **Single developer constraint:**
  The entire system — backend, frontend, AI integration, and cloud
  infrastructure — is designed and built by one developer.

- **Organizational constraint:**
  The project is developed and evaluated in an academic environment
  and is not intended for commercial use.

- **Legal constraint:**
  The system does not handle sensitive financial data and does not comply
  with full commercial data protection or payment regulations.
  Return policy information is informational only and has no legal binding.

- **Scope constraint:**
  The system is intended for personal purchases made in physical stores
  and is not designed for business or commercial invoice management.

- **External service constraint:**
  The system depends on AWS S3 for image storage and the Anthropic API
  for AI extraction. Unavailability of either service will affect
  core functionality.
