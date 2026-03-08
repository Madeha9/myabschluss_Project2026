CREATE TABLE invoice (
  id UUID PRIMARY KEY,
  invoice_date DATE,
  store_name VARCHAR(255),
  total_amount NUMERIC(12,2),
  currency VARCHAR(3),
  return_days INTEGER,
  image_url TEXT,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE invoice_item (
  id UUID PRIMARY KEY,
  invoice_id UUID NOT NULL REFERENCES invoice(id) ON DELETE CASCADE,
  description VARCHAR(255),
  quantity NUMERIC(12,2),
  unit_price NUMERIC(12,2),
  line_total NUMERIC(12,2)
);