import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Invoice, InvoiceResponseDTO, MonthlySpending, UploadResponse} from '../models/invoice.model';

@Injectable({providedIn: 'root'})
export class InvoiceService {

  private readonly base = 'http://localhost:8080';

  constructor(private http: HttpClient) {
  }

  // GET /myinvoices — maps to your getAllInvoices()
  getAll(): Observable<InvoiceResponseDTO[]> {
    return this.http.get<InvoiceResponseDTO[]>(`${this.base}/myinvoices`);
  }

  // GET /myinvoices/{id} — maps to your getInvoice()
  getById(id: string): Observable<InvoiceResponseDTO> {
    return this.http.get<InvoiceResponseDTO>(`${this.base}/myinvoices/${id}`);
  }

  // GET /myinvoices/search?name= — maps to your searchByStore()
  searchByStore(name: string): Observable<Invoice[]> {
    const params = new HttpParams().set('name', name);
    return this.http.get<Invoice[]>(`${this.base}/myinvoices/search`, {params});
  }

  // PUT /myinvoices — maps to your updateInvoice()
  update(invoice: Invoice): Observable<Invoice> {
    return this.http.put<Invoice>(`${this.base}/myinvoices`, invoice);
  }

  // DELETE /myinvoices/{id} — maps to your deleteInvoice()
  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.base}/myinvoices/${id}`);
  }

  // GET /myinvoices/spending?year=&month= — maps to your getMonthlySpending()
  getMonthlySpending(year: number, month: number): Observable<MonthlySpending> {
    const params = new HttpParams()
      .set('year', year)
      .set('month', month);
    return this.http.get<MonthlySpending>(`${this.base}/myinvoices/spending`, {params});
  }

  // POST /UploadInvoice — maps to your uploadInvoice()
  upload(file: File): Observable<UploadResponse> {
    const formData = new FormData();
    // "uploadInvoice" must match your @RestForm("uploadInvoice") on the Java side exactly
    formData.append('uploadInvoice', file, file.name);
    return this.http.post<UploadResponse>(`${this.base}/UploadInvoice`, formData);
  }
}
