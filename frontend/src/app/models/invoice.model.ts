/** * Frontend data blueprints.
 * These interfaces act as a "Contract" to ensure the Angular UI
 * matches the JSON data coming from the Java Backend exactly.
 */

/** Defines the three possible return states for an invoice. */
export type InvoiceStatus = 'RETURNABLE' | 'NON_RETURNABLE';

/** Represents a single row/item found on a receipt. */
export interface InvoiceItem {
  id?: string;
  description: string;
  quantity: number;
  unitPrice: number;
  lineTotal: number;
}

/** The main data model for a processed invoice, including AI and return data. */
export interface Invoice {
  id: string;
  storeName: string;
  invoiceDate: string;       // LocalDate comes as "YYYY-MM-DD" string from Java
  totalAmount: number;
  vatAmount: number;
  currency: string;
  imageUrl: string;          // S3 URL stored in your InvoiceEntity
  invoiceNumber: number;
  returnDays: number;
  status: InvoiceStatus;
  daysLeft: number;
  items: InvoiceItem[];
}

/** The specific Data Transfer Object (DTO) received from the API after an upload. */
export interface InvoiceResponseDTO {
  id: string;
  storeName: string;
  invoiceDate: string;
  totalAmount: number;
  vatAmount: number;
  status: InvoiceStatus;
  daysLeft: number;
  invoiceNumber: number;
  imageUrl: string;
  currency: string;
  items: InvoiceItem[];

}

/** Simple container for the spending dashboard charts. */
export interface MonthlySpending {
  year: number;
  month: number;
  totalSpending: number;
}

/** Wrapper for the standard API response after a successful file upload. */
export interface UploadResponse {
  message: string;
  status: string;
  data: Invoice;
}
