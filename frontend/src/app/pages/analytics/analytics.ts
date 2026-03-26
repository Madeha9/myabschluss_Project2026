import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { InvoiceService } from '../../services/invoice.service';
import { InvoiceResponseDTO, MonthlySpending } from '../../models/invoice.model';

@Component({
  selector: 'app-analytics',
  standalone: true,
  imports: [CommonModule, FormsModule, MatCardModule, MatButtonModule,
    MatSelectModule, MatFormFieldModule, MatIconModule],
  template: `
    <h1 class="page-title">Spending analytics</h1>

    <!-- Filters -->
    <div class="filter-row">
      <mat-form-field appearance="outline">
        <mat-label>Year</mat-label>
        <mat-select [(ngModel)]="selectedYear">
          <mat-option *ngFor="let y of years" [value]="y">{{ y }}</mat-option>
        </mat-select>
      </mat-form-field>

      <mat-form-field appearance="outline">
        <mat-label>Month</mat-label>
        <mat-select [(ngModel)]="selectedMonth">
          <mat-option [value]="0">All months</mat-option>
          <mat-option *ngFor="let m of months; let i = index" [value]="i+1">{{ m }}</mat-option>
        </mat-select>
      </mat-form-field>

      <button mat-flat-button color="primary" (click)="load()">Apply</button>
    </div>

    <!-- Metric cards -->
    <div class="metric-row">
      <mat-card class="metric-card">
        <div class="metric-label">Total spend</div>
        <div class="metric-value">{{ spending()?.totalSpending | number:'1.2-2' }} €</div>
      </mat-card>
      <mat-card class="metric-card">
        <div class="metric-label">Invoices in period</div>
        <div class="metric-value">{{ invoicesInPeriod().length }}</div>
      </mat-card>
      <mat-card class="metric-card">
        <div class="metric-label">Average per invoice</div>
        <div class="metric-value">{{ avgSpend() | number:'1.2-2' }} €</div>
      </mat-card>
    </div>

    <div class="charts-grid">
      <!-- Monthly bar chart -->
      <mat-card>
        <mat-card-header><mat-card-title>Monthly spending — {{ selectedYear }}</mat-card-title></mat-card-header>
        <mat-card-content>
          <div class="bar-chart">
            <div *ngFor="let m of monthlyBars(); let i = index" class="bar-group">
              <div class="bar-value" *ngIf="m.value > 0">{{ m.value | number:'1.0-0' }}</div>
              <div class="bar-wrap">
                <div class="bar" [style.height]="m.height + 'px'"
                     [class.bar-current]="i + 1 === selectedMonth"
                     [class.bar-empty]="m.value === 0"></div>
              </div>
              <div class="bar-label">{{ m.label }}</div>
            </div>
          </div>
        </mat-card-content>
      </mat-card>

      <!-- Top stores -->
      <mat-card>
        <mat-card-header><mat-card-title>Top stores</mat-card-title></mat-card-header>
        <mat-card-content>
          <div *ngFor="let store of topStores()" class="store-row">
            <span class="store-name">{{ store.name }}</span>
            <div class="store-bar-bg">
              <div class="store-bar-fill" [style.width]="store.pct + '%'"></div>
            </div>
            <span class="store-amount">{{ store.total | number:'1.2-2' }} €</span>
          </div>
          <div *ngIf="topStores().length === 0" class="empty">No data for this period</div>
        </mat-card-content>
      </mat-card>
    </div>
  `,
  styles: [`
    .page-title { font-size:22px; font-weight:500; margin-bottom:20px; }
    .filter-row { display:flex; gap:12px; align-items:center; margin-bottom:20px; flex-wrap:wrap; }
    .metric-row { display:grid; grid-template-columns:repeat(3,1fr); gap:12px; margin-bottom:20px; }
    .metric-card { padding:14px 16px !important; }
    .metric-label { font-size:12px; color:#666; margin-bottom:4px; }
    .metric-value { font-size:24px; font-weight:500; }
    .charts-grid { display:grid; grid-template-columns:1fr 1fr; gap:16px; }
    .bar-chart { display:flex; align-items:flex-end; gap:6px; height:160px; padding:8px 0 0; }
    .bar-group { display:flex; flex-direction:column; align-items:center; flex:1; gap:4px; }
    .bar-wrap { display:flex; align-items:flex-end; height:120px; }
    .bar { width:100%; border-radius:4px 4px 0 0; background:#90caf9; min-height:4px; transition:height 0.3s; }
    .bar-current { background:#1565c0; }
    .bar-empty { background:#e0e0e0; }
    .bar-label { font-size:10px; color:#888; }
    .bar-value { font-size:10px; color:#666; }
    .store-row { display:flex; align-items:center; gap:8px; padding:8px 0; border-bottom:1px solid #f0f0f0; font-size:13px; }
    .store-row:last-child { border-bottom:none; }
    .store-name { width:90px; color:#666; overflow:hidden; text-overflow:ellipsis; white-space:nowrap; }
    .store-bar-bg { flex:1; height:6px; background:#e0e0e0; border-radius:4px; overflow:hidden; }
    .store-bar-fill { height:100%; background:#1565c0; border-radius:4px; }
    .store-amount { font-weight:500; min-width:80px; text-align:right; }
    .empty { color:#bdbdbd; text-align:center; padding:20px; }
  `]
})
export class AnalyticsComponent implements OnInit {
  spending = signal<MonthlySpending | null>(null);
  allInvoices = signal<InvoiceResponseDTO[]>([]);
  selectedYear = new Date().getFullYear();
  selectedMonth = new Date().getMonth() + 1;

  years = [2026, 2025, 2024];
  months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];

  constructor(private svc: InvoiceService) {}

  ngOnInit() {
    this.svc.getAll().subscribe(data => { this.allInvoices.set(data); this.load(); });
  }

  load() {
    if (this.selectedMonth > 0) {
      this.svc.getMonthlySpending(this.selectedYear, this.selectedMonth)
        .subscribe(data => this.spending.set(data));
    }
  }

  invoicesInPeriod() {
    return this.allInvoices().filter(i => {
      const d = new Date(i.invoiceDate);
      const yearOk = d.getFullYear() === this.selectedYear;
      return this.selectedMonth === 0 ? yearOk : yearOk && (d.getMonth() + 1) === this.selectedMonth;
    });
  }

  avgSpend(): number {
    const inv = this.invoicesInPeriod();
    if (!inv.length) return 0;
    return inv.reduce((sum, i) => sum + i.totalAmount, 0) / inv.length;
  }

  monthlyBars() {
    const maxVal = Math.max(...this.months.map((_, i) => this.sumForMonth(i + 1)), 1);
    return this.months.map((label, i) => {
      const value = this.sumForMonth(i + 1);
      return { label, value, height: Math.round((value / maxVal) * 110) };
    });
  }

  sumForMonth(month: number): number {
    return this.allInvoices()
      .filter(i => {
        const d = new Date(i.invoiceDate);
        return d.getFullYear() === this.selectedYear && (d.getMonth() + 1) === month;
      })
      .reduce((sum, i) => sum + i.totalAmount, 0);
  }

  topStores() {
    const map = new Map<string, number>();
    this.invoicesInPeriod().forEach(i => {
      map.set(i.storeName, (map.get(i.storeName) ?? 0) + i.totalAmount);
    });
    const max = Math.max(...map.values(), 1);
    return Array.from(map.entries())
      .sort((a, b) => b[1] - a[1])
      .slice(0, 5)
      .map(([name, total]) => ({ name, total, pct: Math.round((total / max) * 100) }));
  }
}
