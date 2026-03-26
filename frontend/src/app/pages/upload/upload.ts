import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { InvoiceService } from '../../services/invoice.service';
import { InvoiceResponseDTO } from '../../models/invoice.model';

type UploadState = 'idle' | 'selected' | 'uploading' | 'success' | 'error';

@Component({
  selector: 'app-upload',
  standalone: true,
  imports: [CommonModule, RouterLink, MatButtonModule, MatIconModule,
    MatCardModule, MatProgressBarModule],
  template: `
    <h1 class="page-title">Upload invoice</h1>

    <mat-card class="upload-card">

      <!-- IDLE: drop zone -->
      <div *ngIf="state() === 'idle'" class="drop-zone"
           [class.drag-over]="isDragging"
           (dragover)="onDragOver($event)"
           (dragleave)="isDragging = false"
           (drop)="onDrop($event)"
           (click)="fileInput.click()">
        <mat-icon class="upload-icon">cloud_upload</mat-icon>
        <p class="drop-title">Drag and drop your invoice here</p>
        <p class="drop-sub">JPG, PNG or PDF — max 10 MB</p>
        <button mat-stroked-button (click)="$event.stopPropagation(); fileInput.click()">
          Browse files
        </button>
        <input #fileInput type="file" hidden accept=".jpg,.jpeg,.png,.pdf"
               (change)="onFileSelected($event)" />
      </div>

      <!-- SELECTED: review -->
      <div *ngIf="state() === 'selected'" class="selected-state">
        <div class="file-row">
          <mat-icon class="file-icon">description</mat-icon>
          <div class="file-info">
            <div class="file-name">{{ selectedFile!.name }}</div>
            <div class="file-meta">{{ fileSize() }} · {{ selectedFile!.type }}</div>
          </div>
          <button mat-icon-button color="warn" (click)="reset()">
            <mat-icon>close</mat-icon>
          </button>
        </div>
        <div class="info-banner">
          <mat-icon style="font-size:16px;width:16px;height:16px">info</mat-icon>
          The AI will extract store name, date, total amount and VAT automatically.
        </div>
        <div class="action-row">
          <button mat-stroked-button (click)="reset()">Cancel</button>
          <button mat-flat-button color="primary" (click)="upload()">
            <mat-icon>auto_awesome</mat-icon> Process invoice
          </button>
        </div>
      </div>

      <!-- UPLOADING -->
      <div *ngIf="state() === 'uploading'" class="uploading-state">
        <mat-icon class="spin-icon">sync</mat-icon>
        <p>Analysing invoice with AI…</p>
        <mat-progress-bar mode="indeterminate" style="margin-top:12px"></mat-progress-bar>
      </div>

      <!-- SUCCESS -->
      <div *ngIf="state() === 'success' && savedInvoice()" class="success-state">
        <mat-icon class="success-icon">check_circle</mat-icon>
        <h2>Invoice saved successfully</h2>
        <p class="muted">{{ savedInvoice()!.storeName }} · {{ savedInvoice()!.totalAmount | number:'1.2-2' }} · {{ savedInvoice()!.invoiceDate }}</p>

        <div class="extracted-grid">
          <div class="extracted-field"><div class="ef-label">Store</div><div>{{ savedInvoice()!.storeName }}</div></div>
          <div class="extrac
          ted-field"><div class="ef-label">Date</div><div>{{ savedInvoice()!.invoiceDate }}</div></div>
          <div class="extracted-field"><div class="ef-label">VAT</div><div>{{ savedInvoice()!.vatAmount | number:'1.2-2' }}</div></div>
          <div class="extracted-field"><div class="ef-label">Invoice #</div><div>{{ savedInvoice()!.invoiceNumber }}</div></div>
          <div class="extracted-field"><div class="ef-label">Total</div>
            <div>{{ savedInvoice()!.totalAmount | number:'1.2-2' }} {{ savedInvoice()!.currency }}</div></div>

          <div class="extracted-field"><div class="ef-label">Status</div><div>{{ savedInvoice()!.status }}</div></div>
        </div>

        <div class="action-row" style="margin-top:20px">
          <button mat-stroked-button (click)="reset()">Upload another</button>
          <button mat-flat-button color="primary" routerLink="/invoices">View invoices</button>
        </div>
      </div>

      <!-- ERROR -->
      <div *ngIf="state() === 'error'" class="error-state">
        <mat-icon class="error-icon">error_outline</mat-icon>
        <h2>Upload failed</h2>
        <p class="muted">{{ errorMessage() }}</p>
        <button mat-stroked-button (click)="reset()">Try again</button>
      </div>

    </mat-card>
  `,
  styles: [`
    .page-title { font-size:22px; font-weight:500; margin-bottom:20px; }
    .upload-card { max-width:560px; padding:24px !important; }
    .drop-zone { border:2px dashed #90caf9; border-radius:12px; padding:40px 20px; text-align:center; cursor:pointer; transition:background 0.2s; }
    .drop-zone:hover, .drag-over { background:#e3f2fd; }
    .upload-icon { font-size:48px; width:48px; height:48px; color:#1565c0; margin-bottom:12px; }
    .drop-title { font-weight:500; font-size:15px; margin:0 0 4px; }
    .drop-sub { font-size:13px; color:#888; margin:0 0 16px; }
    .file-row { display:flex; align-items:center; gap:12px; background:#f5f5f5; border-radius:8px; padding:12px; margin-bottom:12px; }
    .file-icon { color:#1565c0; font-size:32px; width:32px; height:32px; }
    .file-name { font-size:13px; font-weight:500; }
    .file-meta { font-size:12px; color:#888; }
    .file-info { flex:1; }
    .info-banner { display:flex; align-items:flex-start; gap:8px; background:#fff8e1; border-radius:8px; padding:10px 12px; font-size:12px; color:#6d4c00; margin-bottom:16px; }
    .action-row { display:flex; gap:8px; justify-content:flex-end; }
    .uploading-state { text-align:center; padding:32px; }
    .spin-icon { font-size:48px; width:48px; height:48px; color:#1565c0; animation:spin 1s linear infinite; }
    .success-state { text-align:center; }
    .success-icon { font-size:56px; width:56px; height:56px; color:#2e7d32; }
    .error-state { text-align:center; }
    .error-icon { font-size:56px; width:56px; height:56px; color:#c62828; }
    .extracted-grid { display:grid; grid-template-columns:1fr 1fr; gap:10px; margin-top:16px; text-align:left; }
    .extracted-field { background:#f5f5f5; border-radius:8px; padding:10px 12px; font-size:13px; }
    .ef-label { font-size:11px; color:#888; margin-bottom:4px; }
    .muted { color:#888; font-size:13px; }
    @keyframes spin { to { transform:rotate(360deg); } }
  `]
})
export class UploadComponent {
  state = signal<UploadState>('idle');
  savedInvoice = signal<InvoiceResponseDTO | null>(null);
  errorMessage = signal('');
  selectedFile: File | null = null;
  isDragging = false;

  constructor(private svc: InvoiceService) {}

  onDragOver(e: DragEvent) { e.preventDefault(); this.isDragging = true; }
  onDrop(e: DragEvent) {
    e.preventDefault(); this.isDragging = false;
    const file = e.dataTransfer?.files[0];
    if (file) this.setFile(file);
  }
  onFileSelected(e: Event) {
    const file = (e.target as HTMLInputElement).files?.[0];
    if (file) this.setFile(file);
  }
  setFile(file: File) { this.selectedFile = file; this.state.set('selected'); }

  upload() {
    if (!this.selectedFile) return;
    this.state.set('uploading');
    this.svc.upload(this.selectedFile).subscribe({
      next: res => { this.savedInvoice.set(res.data); this.state.set('success'); },
      error: err => { this.errorMessage.set(err.error?.details ?? 'Unknown error'); this.state.set('error'); }
    });
  }

  reset() {
    this.state.set('idle');
    this.selectedFile = null;
    this.savedInvoice.set(null);
    this.errorMessage.set('');
  }

  fileSize(): string {
    if (!this.selectedFile) return '';
    const kb = this.selectedFile.size / 1024;
    return kb > 1024 ? (kb / 1024).toFixed(1) + ' MB' : kb.toFixed(0) + ' KB';
  }
}
