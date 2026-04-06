CREATE TABLE invoice (
    id              UUID PRIMARY KEY,
    store_name      VARCHAR(255),
    invoice_date    DATE,
    total_amount    NUMERIC(12,2),
    vat_amount      NUMERIC(12,2),
    currency        VARCHAR(3),
    image_url       TEXT,
    invoice_number  INTEGER,
    return_days     INTEGER,
    invoice_status  VARCHAR(50),
    created_at      TIMESTAMP NOT NULL,
    updated_at      TIMESTAMP NOT NULL
);

CREATE TABLE invoice_item (
    id          UUID PRIMARY KEY,
    invoice_id  UUID NOT NULL REFERENCES invoice(id) ON DELETE CASCADE,
    description VARCHAR(255),
    quantity    NUMERIC(12,2),
    unit_price  NUMERIC(12,2),
    line_total  NUMERIC(12,2)
);