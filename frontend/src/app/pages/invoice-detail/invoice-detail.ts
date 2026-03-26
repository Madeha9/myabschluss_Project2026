import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatTableModule } from '@angular/material/table';
import { InvoiceService } from '../../services/invoice.service';
import { InvoiceResponseDTO } from '../../models/invoice.model';

@Component({
  selector: 'app-invoice-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, MatButtonModule, MatIconModule,
    MatCardModule, MatDividerModule, MatTableModule],
  template: `
    <div *ngIf="invoice()">
      <div class="page-header">
        <button mat-button routerLink="/invoices">
          <mat-icon>arrow_back</mat-icon> Back
        </button>
        <button mat-stroked-button color="warn" (click)="delete()">
          <mat-icon>delete</mat-icon> Delete
        </button>
      </div>

      <div class="detail-grid">
        <!-- Left: details -->
        <mat-card>
          <mat-card-header>
            <mat-card-title>{{ invoice()!.storeName }}</mat-card-title>
            <mat-card-subtitle>Invoice #{{ invoice()!.invoiceNumber }}</mat-card-subtitle>
          </mat-card-header>
          <mat-card-content>
            <table class="info-table">
              <tr><td class="label">Date</td><td>{{ invoice()!.invoiceDate }}</td></tr>
              <tr><td class="label">Total</td><td><strong>{{ invoice()!.totalAmount | number:'1.2-2' }} {{ invoice()!.currency }}</strong></td></tr>
              <tr><td class="label">VAT</td><td>{{ invoice()!.vatAmount | number:'1.2-2' }}</td></tr>
              <tr><td class="label">Status</td>
                <td><span class="status-badge" [ngClass]="statusClass(invoice()!.status)">
                  {{ invoice()!.status }}</span></td></tr>
              <tr><td class="label">Days left</td>
                <td [ngClass]="invoice()!.daysLeft > 5 ? 'green' : 'orange'">
                  {{ invoice()!.daysLeft > 0 ? invoice()!.daysLeft + ' days' : 'Expired' }}
                </td></tr>
            </table>

            <!-- Return window progress bar -->
            <div style="margin-top:16px" *ngIf="invoice()!.daysLeft > 0">
              <div class="bar-label">Return window</div>
              <div class="progress-track">
                <div class="progress-fill" [style.width]="returnProgress() + '%'"></div>
              </div>
              <div class="bar-sublabel">{{ returnProgress() }}% of return window used</div>
            </div>
          </mat-card-content>
        </mat-card>

        <!-- Right: original image -->
        <mat-card>
          <mat-card-header><mat-card-title>Original invoice</mat-card-title></mat-card-header>
          <mat-card-content>
            <img *ngIf="invoice()!.imageUrl" [src]="invoice()!.imageUrl"
                 class="invoice-img" alt="invoice image" />
            <div *ngIf="!invoice()!.imageUrl" class="no-img">
              <mat-icon>image_not_supported</mat-icon>
              <p>No image available</p>
            </div>
            <div class="url-row" *ngIf="invoice()!.imageUrl">
              <span class="url-text">{{ invoice()!.imageUrl }}</span>
              <a [href]="invoice()!.imageUrl" target="_blank" mat-icon-button>
                <mat-icon>open_in_new</mat-icon>
              </a>
            </div>
          </mat-card-content>
        </mat-card>
      </div>

      <!-- Items table -->
      <mat-card style="margin-top:16px" *ngIf="invoice()!.items?.length">
        <mat-card-header><mat-card-title>Line items</mat-card-title></mat-card-header>
        <mat-card-content>
          <table mat-table [dataSource]="invoice()!.items" style="width:100%">
            <ng-container matColumnDef="description">
              <th mat-header-cell *matHeaderCellDef>Description</th>
              <td mat-cell *matCellDef="let item">{{ item.description }}</td>
            </ng-container>
            <ng-container matColumnDef="quantity">
              <th mat-header-cell *matHeaderCellDef>Qty</th>
              <td mat-cell *matCellDef="let item">{{ item.quantity }}</td>
            </ng-container>
            <ng-container matColumnDef="unitPrice">
              <th mat-header-cell *matHeaderCellDef>Unit price</th>
              <td mat-cell *matCellDef="let item">{{ item.unitPrice | number:'1.2-2' }}</td>
            </ng-container>
            <ng-container matColumnDef="lineTotal">
              <th mat-header-cell *matHeaderCellDef>Line total</th>
              <td mat-cell *matCellDef="let item"><strong>{{ item.lineTotal | number:'1.2-2' }}</strong></td>
            </ng-container>
            <tr mat-header-row *matHeaderRowDef="itemCols"></tr>
            <tr mat-row *matRowDef="let r; columns: itemCols;"></tr>
          </table>
        </mat-card-content>
      </mat-card>
    </div>
  `,
  styles: [`
    .page-header { display:flex; justify-content:space-between; margin-bottom:20px; }
    .detail-grid { display:grid; grid-template-columns:1fr 1fr; gap:16px; }
    .info-table { width:100%; font-size:14px; margin-top:12px; }
    .info-table td { padding:8px 0; }
    .label { color:#888; width:100px; }
    .green { color:#2e7d32; font-weight:500; }
    .orange { color:#e65100; font-weight:500; }
    .status-badge { padding:3px 10px; border-radius:12px; font-size:11px; font-weight:500; }
    .badge-RETURNABLE { background:#e8f5e9; color:#2e7d32; }
    .badge-NON_RETURNABLE { background:#fafafa; color:#616161; }
    .badge-SATISFIED { background:#e3f2fd; color:#1565c0; }
    .progress-track { height:6px; background:#e0e0e0; border-radius:4px; margin:6px 0; }
    .progress-fill { height:100%; background:#1565c0; border-radius:4px; transition:width 0.3s; }
    .bar-label { font-size:12px; color:#666; }
    .bar-sublabel { font-size:11px; color:#999; }
    .invoice-img { width:100%; max-height:320px; object-fit:contain; border-radius:8px; background:#f5f5f5; }
    .no-img { text-align:center; padding:40px; color:#bdbdbd; }
    .url-row { display:flex; align-items:center; margin-top:8px; gap:4px; }
    .url-text { font-size:11px; color:#1565c0; word-break:break-all; flex:1; }
  `]
})
export class InvoiceDetailComponent implements OnInit {
  invoice = signal<InvoiceResponseDTO | null>(null);
  itemCols = ['description', 'quantity', 'unitPrice', 'lineTotal'];

  constructor(private route: ActivatedRoute, private svc: InvoiceService, private router: Router) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id')!;
    this.svc.getById(id).subscribe(data => this.invoice.set(data));
  }

  returnProgress(): number {
    const inv = this.invoice();
    if (!inv || inv.daysLeft === 0) return 100;
    const total = (inv as any).returnDays ?? 30;
    return Math.round(((total - inv.daysLeft) / total) * 100);
  }

  delete() {
    if (confirm('Delete this invoice?')) {
      this.svc.delete(this.invoice()!.id).subscribe(() => this.router.navigate(['/invoices']));
    }
  }

  statusClass(s: string) { return 'badge-' + s; }
}
