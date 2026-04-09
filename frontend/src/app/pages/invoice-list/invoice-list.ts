import {Component, computed, OnInit, signal} from '@angular/core';
import {CommonModule} from '@angular/common';
import {Router} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {MatTableModule} from '@angular/material/table';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatChipsModule} from '@angular/material/chips';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatCardModule} from '@angular/material/card';
import {MatBadgeModule} from '@angular/material/badge';
import {MatDialogModule} from '@angular/material/dialog';
import {MatTooltipModule} from '@angular/material/tooltip';
import {InvoiceService} from '../../services/invoice.service';
import {InvoiceResponseDTO, InvoiceStatus} from '../../models/invoice.model';

@Component({
  imports: [
    CommonModule, FormsModule,
    MatTableModule, MatButtonModule, MatIconModule,
    MatChipsModule, MatInputModule, MatFormFieldModule,
    MatCardModule, MatBadgeModule, MatDialogModule, MatTooltipModule
  ],
  selector: 'app-invoice-list',
  standalone: true,
  styles: [`
    .page-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 20px;
    }
    h1 {
      font-size: 22px;
      font-weight: 500;
      margin: 0;
      color: #880e4f;
    }
    .metric-row {
      display: grid;
      grid-template-columns: repeat(4, 1fr);
      gap: 12px;
      margin-bottom: 20px;
    }
    .metric-card {
      padding: 14px 16px !important;
      border: 0.5px solid #f8bbd0 !important;
      border-radius: 10px !important;
    }
    .metric-label {
      font-size: 12px;
      color: #880e4f;
      margin-bottom: 4px;
    }
    .metric-value {
      font-size: 26px;
      font-weight: 500;
      color: #880e4f;
    }
    .metric-value.returnable { color: #2e7d32; }
    .metric-value.expiring   { color: #e65100; }
    .metric-value.expired    { color: #c62828; }

    .filter-row {
      display: flex;
      align-items: center;
      gap: 16px;
      margin-bottom: 16px;
      flex-wrap: wrap;
    }
    .search-field { flex: 1; min-width: 200px; }
    .filter-chips { display: flex; gap: 8px; flex-wrap: wrap; }

    .active-chip {
      background: #fce4ec !important;
      color: #880e4f !important;
      border-color: #f48fb1 !important;
    }

    .table-wrapper {
      background: white;
      border-radius: 8px;
      overflow: hidden;
      border: 0.5px solid #f8bbd0;
    }

    ::ng-deep .mat-mdc-header-row {
      background: #fce4ec !important;
    }
    ::ng-deep .mat-mdc-header-cell {
      color: #880e4f !important;
      font-weight: 500 !important;
    }

    ::ng-deep .mat-mdc-row:nth-child(even) td {
      background: #fff5f8;
    }
    ::ng-deep .mat-mdc-row:hover td {
      background: #fce4ec !important;
      cursor: pointer;
    }

    .invoice-thumb {
      width: 36px;
      height: 44px;
      object-fit: cover;
      border-radius: 4px;
      border: 1px solid #f8bbd0;
      cursor: pointer;
    }
    .thumb-cell  { width: 36px; cursor: pointer; }
    .no-img-icon { color: #f48fb1; font-size: 28px; }
    .muted       { color: #ad6f87; }

    .status-badge {
      padding: 3px 10px;
      border-radius: 12px;
      font-size: 11px;
      font-weight: 500;
    }
    .badge-returnable     { background: #e8f5e9; color: #2e7d32; }
    .badge-non-returnable { background: #fce4ec; color: #880e4f; }
    .badge-expiring       { background: #fff3e0; color: #e65100; }

    .days-ok   { color: #2e7d32; font-weight: 500; }
    .days-warn { color: #e65100; font-weight: 500; }

    .empty-state { text-align: center; padding: 48px; color: #f48fb1; }
    .empty-state mat-icon { font-size: 48px; width: 48px; height: 48px; }

    .img-overlay {
      position: fixed;
      inset: 0;
      background: rgba(136,14,79,0.5);
      z-index: 1000;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .img-modal {
      background: white;
      border-radius: 12px;
      padding: 20px;
      width: 400px;
      max-width: 90vw;
      border: 1px solid #f8bbd0;
    }
    .img-modal-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 12px;
    }
    .modal-img {
      width: 100%;
      max-height: 300px;
      object-fit: contain;
      border-radius: 8px;
      background: #fce4ec;
    }
    .img-url-row {
      display: flex;
      align-items: center;
      gap: 6px;
      margin-top: 10px;
    }
    .img-url {
      font-size: 11px;
      color: #880e4f;
      word-break: break-all;
    }
  `],
  template: `
    <div class="page-header">
      <h1>Invoices</h1>
      <button mat-flat-button color="primary" (click)="router.navigate(['/upload'])">
        <mat-icon>add</mat-icon>
        Upload invoice
      </button>
    </div>

    <div class="metric-row">
      <mat-card class="metric-card">
        <div class="metric-label">Total invoices</div>
        <div class="metric-value">{{ invoices().length }}</div>
      </mat-card>
      <mat-card class="metric-card">
        <div class="metric-label">Returnable</div>
        <div class="metric-value returnable">{{ returnableCount() }}</div>
      </mat-card>
      <mat-card class="metric-card">
        <div class="metric-label">Expiring in 5 days</div>
        <div class="metric-value expiring">{{ expiringCount() }}</div>
      </mat-card>
      <mat-card class="metric-card">
        <div class="metric-label">Expired</div>
        <div class="metric-value expired">{{ expiredCount() }}</div>
      </mat-card>
    </div>

    <div class="filter-row">
      <mat-form-field appearance="outline" class="search-field">
        <mat-label>Search by store name</mat-label>
        <input matInput [(ngModel)]="searchTerm" (ngModelChange)="onSearch($event)"/>
        <mat-icon matSuffix>search</mat-icon>
      </mat-form-field>

      <div class="filter-chips">
        <button mat-stroked-button
                [class.active-chip]="activeFilters.has('RETURNABLE')"
                (click)="toggleFilter('RETURNABLE')">
          <mat-icon>check_circle</mat-icon> Returnable
        </button>
        <button mat-stroked-button
                [class.active-chip]="activeFilters.has('NON_RETURNABLE')"
                (click)="toggleFilter('NON_RETURNABLE')">
          <mat-icon>cancel</mat-icon> Non-returnable
        </button>
      </div>
    </div>

    <div class="table-wrapper mat-elevation-z1">
      <table mat-table [dataSource]="filteredInvoices()">

        <ng-container matColumnDef="image">
          <th mat-header-cell *matHeaderCellDef>Image</th>
          <td mat-cell *matCellDef="let inv">
            <div class="thumb-cell" (click)="openImageModal(inv)">
              <img *ngIf="inv.imageUrl" [src]="inv.imageUrl"
                   class="invoice-thumb" alt="invoice"
                   (error)="onImgError($event)"/>
              <mat-icon *ngIf="!inv.imageUrl" class="no-img-icon">description</mat-icon>
            </div>
          </td>
        </ng-container>

        <ng-container matColumnDef="invoiceNumber">
          <th mat-header-cell *matHeaderCellDef>Number</th>
          <td mat-cell *matCellDef="let inv" class="muted">{{ inv.invoiceNumber ?? '—' }}</td>
        </ng-container>

        <ng-container matColumnDef="storeName">
          <th mat-header-cell *matHeaderCellDef>Store</th>
          <td mat-cell *matCellDef="let inv"><strong>{{ inv.storeName }}</strong></td>
        </ng-container>

        <ng-container matColumnDef="invoiceDate">
          <th mat-header-cell *matHeaderCellDef>Date</th>
          <td mat-cell *matCellDef="let inv" class="muted">{{ inv.invoiceDate }}</td>
        </ng-container>

        <ng-container matColumnDef="totalAmount">
          <th mat-header-cell *matHeaderCellDef>Amount</th>
          <td mat-cell *matCellDef="let inv">
            {{ inv.totalAmount | number:'1.2-2' }} {{ inv.currency }}
          </td>
        </ng-container>

        <ng-container matColumnDef="vatAmount">
          <th mat-header-cell *matHeaderCellDef>VAT</th>
          <td mat-cell *matCellDef="let inv" class="muted">
            {{ inv.vatAmount | number:'1.2-2' }}
          </td>
        </ng-container>

        <ng-container matColumnDef="status">
          <th mat-header-cell *matHeaderCellDef>Status</th>
          <td mat-cell *matCellDef="let inv">
            <span class="status-badge" [ngClass]="statusClass(inv.status)">
              {{ statusLabel(inv.status) }}
            </span>
          </td>
        </ng-container>

        <ng-container matColumnDef="daysLeft">
          <th mat-header-cell *matHeaderCellDef>Days left</th>
          <td mat-cell *matCellDef="let inv">
            <span *ngIf="inv.daysLeft > 0" [ngClass]="daysClass(inv.daysLeft)">
              {{ inv.daysLeft }} d
            </span>
            <span *ngIf="inv.daysLeft === 0" class="muted">—</span>
          </td>
        </ng-container>

        <ng-container matColumnDef="actions">
          <th mat-header-cell *matHeaderCellDef></th>
          <td mat-cell *matCellDef="let inv">
            <button mat-icon-button
                    matTooltip="View details"
                    (click)="navigateTo(inv.id)">
              <mat-icon>chevron_right</mat-icon>
            </button>
            <button mat-icon-button
                    color="warn"
                    matTooltip="Delete"
                    (click)="deleteInvoice(inv.id)">
              <mat-icon>delete_outline</mat-icon>
            </button>
          </td>
        </ng-container>

        <tr mat-header-row *matHeaderRowDef="columns"></tr>
        <tr mat-row *matRowDef="let row; columns: columns;"></tr>
      </table>

      <div *ngIf="filteredInvoices().length === 0" class="empty-state">
        <mat-icon>inbox</mat-icon>
        <p>No invoices found</p>
      </div>
    </div>

    <div class="img-overlay" *ngIf="selectedInvoice" (click)="closeImageModal()">
      <div class="img-modal" (click)="$event.stopPropagation()">
        <div class="img-modal-header">
          <div>
            <strong>{{ selectedInvoice.storeName }}</strong>
            <span class="muted" style="margin-left:8px">{{ selectedInvoice.invoiceDate }}</span>
          </div>
          <button mat-icon-button (click)="closeImageModal()">
            <mat-icon>close</mat-icon>
          </button>
        </div>
        <img [src]="selectedInvoice.imageUrl" class="modal-img" alt="invoice"/>
        <div class="img-url-row">
          <mat-icon style="font-size:14px">link</mat-icon>
          <span class="img-url">{{ selectedInvoice.imageUrl }}</span>
        </div>
        <a [href]="selectedInvoice.imageUrl" target="_blank" mat-stroked-button style="margin-top:12px">
          <mat-icon>open_in_new</mat-icon> Open original
        </a>
      </div>
    </div>
  `
})
export class InvoiceListComponent implements OnInit {
  invoices = signal<InvoiceResponseDTO[]>([]);
  filteredInvoices = signal<InvoiceResponseDTO[]>([]);
  searchTerm = '';
  activeFilters = new Set<InvoiceStatus>();
  selectedInvoice: InvoiceResponseDTO | null = null;

  columns = ['image','invoiceNumber','storeName','invoiceDate',
    'totalAmount','vatAmount','status','daysLeft','actions'];

  returnableCount = computed(() =>
    this.invoices().filter(i => i.status === 'RETURNABLE').length);
  expiringCount = computed(() =>
    this.invoices().filter(i => i.status === 'RETURNABLE' && i.daysLeft <= 5 && i.daysLeft > 0).length);
  expiredCount = computed(() =>
    this.invoices().filter(i => i.status === 'NON_RETURNABLE').length);

  constructor(private svc: InvoiceService, public router: Router) {}

  ngOnInit() {
    this.svc.getAll().subscribe(data => {
      this.invoices.set(data);
      this.applyFilters();
    });
  }

  navigateTo(id: string) {
    this.router.navigate(['/invoices', id]);
  }

  onSearch(term: string) {
    if (term && term.length > 0) {
      this.svc.searchByStore(term).subscribe(data => {
        this.invoices.set(data);
        this.applyFilters();
      });
    } else if (term.length === 0) {
      this.svc.getAll().subscribe(data => {
        this.invoices.set(data);
        this.applyFilters();
      });
    }
  }

  toggleFilter(status: InvoiceStatus) {
    this.activeFilters.has(status)
      ? this.activeFilters.delete(status)
      : this.activeFilters.add(status);
    this.applyFilters();
  }

  applyFilters() {
    let result = this.invoices();
    if (this.activeFilters.size > 0) {
      result = result.filter(i => this.activeFilters.has(i.status));
    }
    this.filteredInvoices.set(result);
  }

  deleteInvoice(id: string) {
    if (confirm('Delete this invoice?')) {
      this.svc.delete(id).subscribe(() => {
        this.invoices.update(list => list.filter(i => i.id !== id));
        this.applyFilters();
      });
    }
  }

  openImageModal(inv: InvoiceResponseDTO) { this.selectedInvoice = inv; }
  closeImageModal() { this.selectedInvoice = null; }
  onImgError(e: Event) { (e.target as HTMLImageElement).style.display = 'none'; }

  statusLabel(s: InvoiceStatus) {
    return s === 'RETURNABLE' ? 'Returnable' : 'Non-returnable';
  }

  statusClass(s: InvoiceStatus) {
    return s === 'RETURNABLE' ? 'badge-returnable' : 'badge-non-returnable';
  }

  daysClass(d: number) { return d <= 5 ? 'days-warn' : 'days-ok'; }
}
