# User Guide — IntelliInvoice

**Last Updated:** April 2026
**Author:** Madeha Mohammed

---

## Introduction

IntelliInvoice allows you to upload retail invoice images or PDFs,
automatically extract all data using Claude Sonnet AI, track return
deadlines, and analyse your spending — with zero manual data entry.

---

## System Requirements

- Modern browser (Chrome, Firefox, or Edge)
- Quarkus backend running on http://localhost:8080
- Angular frontend running on http://localhost:4200

For setup instructions see the [Setup Guide](setup.md).

---

## Main Features

### 1. Upload Invoice

1. Click **Upload** in the sidebar
2. Drag and drop an invoice image or click **Browse files**
   - Supported formats: JPG, PNG, PDF
3. Click **Process invoice**
4. Wait while Claude Sonnet AI extracts the data
5. Review the extracted fields on the success screen
6. Invoice is automatically saved and visible in the list

> If the image is blurry or unreadable the system will show
> an error — try uploading a clearer photo.

---

### 2. View Invoice List

1. Click **Invoices** in the sidebar
2. All stored invoices are displayed in a table showing:
   - Image thumbnail
   - Store name, date, total amount, VAT
   - Return status badge and days remaining
3. **Search** by store name using the search field
4. **Filter** by return status using the filter chips:
   - Returnable — items still within return window
   - Non-returnable — return window expired
5. Click the **thumbnail** to view the original invoice image
6. Click the **→ button** to open the full invoice detail

---

### 3. View Invoice Detail

1. Click the **→ button** on any invoice row
2. The detail page shows:
   - All extracted fields and line items
   - Return window progress bar and days remaining
   - Original invoice image loaded from AWS S3
3. Click **Open original** to open the full image in a new tab
4. Click **Delete** to remove the invoice

> If the extracted data is wrong — delete the invoice
> and upload again with a better quality image.
> Direct field editing is not supported in this version.

---

### 4. Spending Analytics

1. Click **Analytics** in the sidebar
2. Select a **year** and **month** and click **Apply**
3. Three charts are displayed:
   - Monthly bar chart — spending per month with colour per month
   - Quarterly donut chart — Q1 to Q4 breakdown
   - Top stores chart — top 5 stores by total spend
4. Metric cards show total spend, invoice count, and average

---

### 5. Delete Invoice

1. Open the invoice detail page or find the invoice in the list
2. Click the **Delete** button
3. Confirm the deletion in the dialog
4. Invoice and all line items are permanently removed

---

## Troubleshooting

| Problem                      | Solution                                                 |
|------------------------------|----------------------------------------------------------|
| Upload fails — unknown error | Check CORS filter is running and backend is on port 8080 |
| AI extraction fails          | Upload a clearer, well-lit invoice image                 |
| Invoice image not showing    | Check imageUrl is returned by the backend API            |
| Charts not rendering         | Make sure at least one invoice exists in the database    |
| Backend not starting         | Check AWS environment variables are set in IntelliJ      |

---

## Known Limitations

- No user login — single user system only
- Camera capture not supported — upload existing files only
- Field editing not supported — delete and re-upload to correct data
- AI accuracy depends on invoice image quality

---

## Related Documentation

- [Setup Guide](setup.md)
- [API Documentation](api/endpoints.md)
- [Architecture Overview](architecture/overview.md)