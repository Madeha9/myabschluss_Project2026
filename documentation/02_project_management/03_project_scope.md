# Project Scope

## In Scope

The project includes the following features and functionality:

- Storage and management of **digital invoices** for physical retail purchases
- Uploading and viewing invoices (e.g. PDF or image files)
- **QR code extraction** from invoice images or PDF files
- Storage of extracted **QR code data** linked to the corresponding invoice
- **Regeneration of QR codes** from stored data to enable reuse (e.g. for product returns when the original invoice is lost)
- Searching and filtering stored invoices (by date, store, or product category)
- Support for **clothing and shoe products only** (no food or other product categories)
- Basic **AI-assisted features** at a prototype level, such as:
    - Suggestions related to return eligibility
    - Decision support based on stored invoice data
- **Monthly and temporal analysis** of stored invoices to identify:
  - Frequently visited shops
  - Shops offering better price–value balance
  - Recurring discount periods (e.g. beginning-of-year sales, seasonal discounts, Black Friday)
  - Price trends across different times of the year
- AI-assisted recommendations such as:
  - Suggesting shops with competitive pricing
  - Advising whether purchasing a membership or loyalty card could be beneficial
  - Recommending the **best time to shop** to obtain products at a reasonable price
- Local or server-based data storage for invoice information
- User interface for managing invoices, viewing analysis results, and receiving suggestions
- Operation in a **physical retail context** (not an online shop)
- **Post-purchase experience evaluation** after uploading an invoice
- Users can rate their shopping experience based on:
  - Overall experience in the shop
  - Customer service quality
  - Price satisfaction
  - Product variety and availability of different colors
- Ratings are collected using a **1–5 evaluation scale**, where:
  - 1 represents the best experience , 5 represents the worst experience
- Collected ratings are stored and used for **analysis and AI-assisted recommendations**



## Out of Scope

The following functionality is explicitly excluded from the project:

- Online shopping or e-commerce functionality
- Online payment processing or access to bank or credit card information
- Integration with real retail systems or point-of-sale systems
- Real-time product availability or pricing
- Food, grocery, or service-related invoices
- Fully autonomous or production-ready AI agents
- Legal validation of invoices or guarantees of return acceptance by retailers
- **Public reviews or shared shop ratings:** customer service evaluations are stored **only for personal use** 
- and are not published, shared, or visible to other users
- Native mobile application deployment is out of scope. The system is designed as a desktop-first web application. Cloud deployment is optional and used only for demonstration purposes.



## Constraints

The project is subject to the following constraints:

- **Time constraint:**  
  The system is developed within a limited timeframe as part of a final student project.

- **Technical constraint:**  
  AI-related features are implemented at a prototype level and are not production-ready.

- **Organizational constraint:**  
  The project is developed and evaluated in an academic environment and is not intended for commercial use.

- **Legal constraint:**  
  The system does not handle sensitive financial data and does not comply with full commercial data protection 
   or payment regulations.

- **Scope constraint:**  
  The system is limited to physical retail invoices for clothing and shoe products only.

