import {AfterViewInit, Component, ElementRef, OnInit, signal, ViewChild} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {MatSelectModule} from '@angular/material/select';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {InvoiceService} from '../../services/invoice.service';
import {InvoiceResponseDTO, MonthlySpending} from '../../models/invoice.model';
import {Chart, registerables} from 'chart.js';

Chart.register(...registerables);

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

      <button mat-flat-button color="primary" (click)="onApply()">Apply</button>
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

    <!-- Monthly chart -->
    <mat-card style="margin-bottom:16px;">
      <mat-card-header>
        <mat-card-title>Monthly spending — {{ selectedYear }}</mat-card-title>
      </mat-card-header>
      <mat-card-content>
        <div class="legend-row">
          <ng-container *ngFor="let m of months; let i = index">
            <span class="legend-item" *ngIf="sumForMonth(i+1) > 0">
              <span class="legend-dot" [style.background]="monthColors[i]"></span>
              {{ m }} € {{ sumForMonth(i + 1) | number:'1.0-0' }}
            </span>
          </ng-container>
        </div>
        <div style="position:relative; width:100%; height:260px;">
          <canvas #monthlyChart></canvas>
        </div>
      </mat-card-content>
    </mat-card>

    <!-- Quarter + store charts -->
    <div class="charts-grid">
      <mat-card>
        <mat-card-header>
          <mat-card-title>Spending by quarter</mat-card-title>
        </mat-card-header>
        <mat-card-content>
          <div style="position:relative; width:100%; height:240px;">
            <canvas #quarterChart></canvas>
          </div>
        </mat-card-content>
      </mat-card>

      <mat-card>
        <mat-card-header>
          <mat-card-title>Top stores</mat-card-title>
        </mat-card-header>
        <mat-card-content>
          <div style="position:relative; width:100%; height:240px;">
            <canvas #storeChart></canvas>
          </div>
        </mat-card-content>
      </mat-card>
    </div>
  `,
  styles: [`
    .page-title {
      font-size: 22px;
      font-weight: 500;
      margin-bottom: 20px;
      color: #880e4f;
    }
    .filter-row { display:flex; gap:12px; align-items:center; margin-bottom:20px; flex-wrap:wrap; }
    .metric-row { display:grid; grid-template-columns:repeat(3,1fr); gap:12px; margin-bottom:20px; }

    .metric-card {
      padding: 14px 16px !important;
      border: 0.5px solid #f8bbd0 !important;
    }

    .metric-label {
      font-size: 12px;
      color: #880e4f;
      margin-bottom: 4px;
    }

    .metric-value {
      font-size: 24px;
      font-weight: 500;
      color: #880e4f;
    }
    .charts-grid { display:grid; grid-template-columns:1fr 1fr; gap:16px; }

    .legend-row {
      display: flex;
      flex-wrap: wrap;
      gap: 10px;
      margin-bottom: 14px;
      padding-top: 8px;
    }

    .legend-item {
      display: flex;
      align-items: center;
      gap: 5px;
      font-size: 11px;
      color: #880e4f;
    }

    .legend-dot {
      width: 10px;
      height: 10px;
      border-radius: 2px;
      display: inline-block;
      flex-shrink: 0;
    }
  `]
})
export class AnalyticsComponent implements OnInit, AfterViewInit {

  @ViewChild('monthlyChart') monthlyChartRef!: ElementRef;
  @ViewChild('quarterChart') quarterChartRef!: ElementRef;
  @ViewChild('storeChart') storeChartRef!: ElementRef;
  monthColors = [
    '#E91E63', '#9C27B0', '#3F51B5', '#2196F3',
    '#00BCD4', '#4CAF50', '#8BC34A', '#FFEB3B',
    '#FF9800', '#FF5722', '#795548', '#607D8B'
  ];
  private monthlyChartInstance: Chart | null = null;
  private quarterChartInstance: Chart | null = null;

  spending = signal<MonthlySpending | null>(null);
  allInvoices = signal<InvoiceResponseDTO[]>([]);

  selectedYear = new Date().getFullYear();
  selectedMonth = new Date().getMonth() + 1;

  years = [2026, 2025, 2024];
  months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
  private storeChartInstance: Chart | null = null;

  constructor(private svc: InvoiceService) {}

  ngOnInit() {
    this.svc.getAll().subscribe(data => {
      this.allInvoices.set(data);
      this.load();
    });
  }

  ngAfterViewInit() {
    setTimeout(() => this.buildCharts(), 100);
  }

  onApply() {
    this.load();
  }

  load() {
    if (this.selectedMonth > 0) {
      this.svc.getMonthlySpending(this.selectedYear, this.selectedMonth)
        .subscribe(data => {
          this.spending.set(data);
          setTimeout(() => this.buildCharts(), 0);
        });
    } else {
      setTimeout(() => this.buildCharts(), 0);
    }
  }

  buildCharts() {
    const vals = this.months.map((_, i) => this.sumForMonth(i + 1));
    const maxVal = Math.max(...vals, 1);

    this.monthlyChartInstance?.destroy();
    this.quarterChartInstance?.destroy();
    this.storeChartInstance?.destroy();

    // monthly bar chart — each month its own colour
    this.monthlyChartInstance = new Chart(
      this.monthlyChartRef.nativeElement, {
        type: 'bar',
        data: {
          labels: this.months,
          datasets: [{
            data: vals,
            backgroundColor: this.monthColors,
            borderRadius: 6,
            borderSkipped: false
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            legend: {display: false},
            tooltip: {
              callbacks: {
                label: (ctx: any) => ctx.raw > 0
                  ? '€ ' + ctx.raw.toLocaleString()
                  : 'No data'
              }
            }
          },
          scales: {
            x: {
              grid: {display: false},
              ticks: {
                color: '#ad6f87',
                font: {size: 11},
                autoSkip: false
              }
            },
            y: {
              beginAtZero: true,
              grid: {color: 'rgba(233,30,99,0.08)'},
              ticks: {
                color: '#ad6f87',
                font: {size: 11},
                callback: (v: any) => '€' + (v / 1000).toFixed(0) + 'k'
              }
            }
          }
        }
      });

    // quarter donut chart
    this.quarterChartInstance = new Chart(
      this.quarterChartRef.nativeElement, {
        type: 'doughnut',
        data: {
          labels: ['Q1 (Jan–Mar)', 'Q2 (Apr–Jun)', 'Q3 (Jul–Sep)', 'Q4 (Oct–Dec)'],
          datasets: [{
            data: [
              vals.slice(0, 3).reduce((a, b) => a + b, 0),
              vals.slice(3, 6).reduce((a, b) => a + b, 0),
              vals.slice(6, 9).reduce((a, b) => a + b, 0),
              vals.slice(9, 12).reduce((a, b) => a + b, 0),
            ],
            backgroundColor: ['#E91E63', '#9C27B0', '#3F51B5', '#2196F3'],
            borderWidth: 0,
            hoverOffset: 6
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          cutout: '60%',
          plugins: {
            legend: {
              position: 'bottom',
              labels: {
                color: '#880e4f',
                boxWidth: 10,
                font: {size: 11},
                padding: 8
              }
            },
            tooltip: {
              callbacks: {
                label: (ctx: any) => ctx.label + ': € ' + ctx.raw.toLocaleString()
              }
            }
          }
        }
      });

    // top stores horizontal bar chart
    const stores = this.topStores();
    this.storeChartInstance = new Chart(
      this.storeChartRef.nativeElement, {
        type: 'bar',
        data: {
          labels: stores.map(s => s.name),
          datasets: [{
            data: stores.map(s => s.total),
            backgroundColor: ['#E91E63', '#9C27B0', '#FF9800', '#4CAF50', '#607D8B'],
            borderRadius: 4,
            borderSkipped: false
          }]
        },
        options: {
          indexAxis: 'y' as const,
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            legend: {display: false},
            tooltip: {
              callbacks: {
                label: (ctx: any) => '€ ' + ctx.raw.toLocaleString()
              }
            }
          },
          scales: {
            x: {
              grid: {color: 'rgba(233,30,99,0.08)'},
              ticks: {
                color: '#ad6f87',
                font: {size: 10},
                callback: (v: any) => '€' + v
              }
            },
            y: {
              grid: {display: false},
              ticks: {color: '#880e4f', font: {size: 11}}
            }
          }
        }
      });
  }

  invoicesInPeriod() {
    return this.allInvoices().filter(i => {
      const d = new Date(i.invoiceDate);
      const yearOk = d.getFullYear() === this.selectedYear;
      return this.selectedMonth === 0
        ? yearOk
        : yearOk && (d.getMonth() + 1) === this.selectedMonth;
    });
  }

  avgSpend(): number {
    const inv = this.invoicesInPeriod();
    if (!inv.length) return 0;
    return inv.reduce((sum, i) => sum + i.totalAmount, 0) / inv.length;
  }

  sumForMonth(month: number): number {
    return this.allInvoices()
      .filter(i => {
        const d = new Date(i.invoiceDate);
        return d.getFullYear() === this.selectedYear
          && (d.getMonth() + 1) === month;
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
      .map(([name, total]) => ({
        name,
        total,
        pct: Math.round((total / max) * 100)
      }));
  }
}
