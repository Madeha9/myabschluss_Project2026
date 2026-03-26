import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, MatListModule, MatIconModule],
  template: `
    <nav class="sidebar">
      <div class="sidebar-logo">
        <mat-icon>receipt_long</mat-icon>
        <span>IntelliInvoice</span>
      </div>

      <mat-nav-list>
        <a mat-list-item routerLink="/invoices" routerLinkActive="active-link">
          <mat-icon matListItemIcon>list_alt</mat-icon>
          <span matListItemTitle>Invoices</span>
        </a>
        <a mat-list-item routerLink="/upload" routerLinkActive="active-link">
          <mat-icon matListItemIcon>upload_file</mat-icon>
          <span matListItemTitle>Upload</span>
        </a>
        <a mat-list-item routerLink="/analytics" routerLinkActive="active-link">
          <mat-icon matListItemIcon>bar_chart</mat-icon>
          <span matListItemTitle>Analytics</span>
        </a>
      </mat-nav-list>
    </nav>
  `,
  styles: [`
    .sidebar {
      width: 220px;
      background: #880e4f;
      color: white;
      display: flex;
      flex-direction: column;
      flex-shrink: 0;
    }
    .sidebar-logo {
      display: flex;
      align-items: center;
      gap: 10px;
      padding: 20px 16px;
      font-size: 16px;
      font-weight: 500;
      border-bottom: 1px solid rgba(255,255,255,0.15);
      color: white;
    }
    mat-nav-list { padding-top: 8px; }
    a[mat-list-item] { color: rgba(255,255,255,0.7) !important; border-radius: 8px; margin: 2px 8px; }
    a[mat-list-item]:hover { background: rgba(255,255,255,0.08) !important; color: white !important; }
    .active-link { background: rgba(248,187,208,0.2) !important; color: #f8bbd0 !important; }
    mat-icon { color: inherit !important; }
  `]
})
export class SidebarComponent {}
