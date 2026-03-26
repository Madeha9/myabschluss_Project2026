import { Routes } from '@angular/router';
import { InvoiceListComponent } from './pages/invoice-list/invoice-list';
import { InvoiceDetailComponent } from './pages/invoice-detail/invoice-detail';
import { UploadComponent } from './pages/upload/upload';
import { AnalyticsComponent } from './pages/analytics/analytics';

export const routes: Routes = [
  { path: '',           redirectTo: 'invoices', pathMatch: 'full' },
  { path: 'invoices',   component: InvoiceListComponent },
  { path: 'invoices/:id', component: InvoiceDetailComponent },
  { path: 'upload',     component: UploadComponent },
  { path: 'analytics',  component: AnalyticsComponent }
];
