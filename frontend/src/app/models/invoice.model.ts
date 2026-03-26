export type InvoiceStatus = 'RETURNABLE' | 'NON_RETURNABLE' | 'SATISFIED';

export interface InvoiceItem {
  id?: string;
  description: string;
  quantity: number;
  unitPrice: number;
  lineTotal: number;
}

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

export interface MonthlySpending {
  year: number;
  month: number;
  totalSpending: number;
}

export interface UploadResponse {
  message: string;
  status: string;
  data: Invoice;
}
