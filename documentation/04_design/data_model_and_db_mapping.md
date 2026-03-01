
# Data Model and Database Mapping

## Overview

This document describes the IntelliInvoice data model and how it maps to the PostgreSQL database schema.
The system stores extracted invoice data and references the original invoice image stored in cloud storage.

## Conceptual Data Model

The core concept is an **Invoice** with:

- invoice header data (date, store, total, product information)
- a list of **InvoiceItems**
- a reference to the **original invoice image** (stored externally)

### Entity-Relationship Diagram

**Invoice (1) ─── (N) InvoiceItem**

![database ER diagram.png](database%20ER%20diagram.png)

## Logical Data Model

### Entity 1: Invoice

**Description:** Stores extracted invoice header information and a reference to the stored invoice image.

**Attributes:**

- `id`: UUID - Primary Key
- `invoiceDate`: DATE - Invoice date
- `storeName`: VARCHAR - Store name
- `totalAmount`: DECIMAL - Total amount
- `currency`: VARCHAR(3) - Currency code (e.g., EUR)
- `imageUrl`: TEXT - Link/path to stored original invoice image
- `createdAt`: TIMESTAMP - Creation timestamp
- `updatedAt`: TIMESTAMP - Last update timestamp

**Relationships:**

- One `Invoice` has many `InvoiceItem`
-

### Entity 2: InvoiceItem

**Description:** Stores individual line items of an invoice.

**Attributes:**

- `id`: UUID - Primary Key
- `invoiceId`: UUID - Foreign Key → Invoice(id)
- `description`: VARCHAR - Item name/description
- `quantity`: DECIMAL - Quantity
- `unitPrice`: DECIMAL - Price per unit
- `lineTotal`: DECIMAL - Total for this item

**Relationships:**

- Many `InvoiceItem` belong to one `Invoice`

## Physical Data Model (Database Schema)

### Table: invoice
```sql
CREATE TABLE invoice
(
    id           UUID PRIMARY KEY,
    invoice_date DATE,
    store_name   VARCHAR(255),
    total_amount NUMERIC(12, 2),
    currency     VARCHAR(3),
    image_url    TEXT,
    return_days  INTEGER,
    created_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP

);
```

**Indexes:**

- `idx_invoice_date` on `invoice_date` (faster filtering by date)
- `idx_invoice_store_name` on `store_name` (faster search by store)

**Constraints:**

- id is PRIMARY KEY
- currency uses VARCHAR(3) to store ISO codes like EUR
- total_amount uses NUMERIC(12,2) for accurate money values

### Table:invoice_item
```sql
CREATE TABLE invoice_item
(
    id          UUID PRIMARY KEY,
    invoice_id  UUID NOT NULL REFERENCES invoice (id) ON DELETE CASCADE,
    description VARCHAR(255),
    quantity    NUMERIC(12, 2),
    unit_price  NUMERIC(12, 2),
    line_total  NUMERIC(12, 2)
);
```

**Indexes:**

- `idx_invoice_item_invoice_id` on `invoice_id`

**Constraints:**

- invoice_id is a FOREIGN KEY → invoice(id)
- ON DELETE CASCADE ensures invoice items are removed automatically when an invoice is deleted


## Object-Relational Mapping (ORM)

### Mapping Strategy

For the first version, the project uses **plain JDBC** (no ORM framework).
SQL queries and result mapping are handled manually using `DataSource`, `PreparedStatement`, and `ResultSet`.

### Class to Table Mappings (manual mapping)

- `Invoice` → `invoice`
- `InvoiceItem` → `invoice_item`

## Data Migration Strategy

For the first version, schema changes are handled **manually**:

- SQL scripts are stored in the repository (e.g., `documentation/db/sql/`).
- Changes are applied locally using a SQL client (psql / pgAdmin).

Future improvement :

- Introduce **Flyway** for versioned migrations (e.g., `V1__init.sql`, `V2__add_return_days.sql`).

## Database Optimization

- Use indexes on frequently filtered/search columns: `invoice_date`, `store_name`, and `invoice_item.invoice_id`.
- Store the original invoice image externally (cloud storage) and keep only `image_url` in the database to reduce DB
  size.

## Backup and Recovery

Local development backup strategy:

- Create backups using `pg_dump`:
    - Backup: `pg_dump intelliinvoice > backup.sql`
    - Restore: `psql intelliinvoice < backup.sql`

Future improvement (optional):

- Automated scheduled backups and retention policy for production deployments.


