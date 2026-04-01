package at.madeha.intelliinvoice.service.mapper;

import at.madeha.intelliinvoice.business.Invoice;
import at.madeha.intelliinvoice.business.InvoiceItem;
import at.madeha.intelliinvoice.business.InvoiceStatus;
import at.madeha.intelliinvoice.database.InvoiceEntity;
import at.madeha.intelliinvoice.database.InvoiceItemEntity;
import at.madeha.intelliinvoice.service.InvoiceReturnService;
import at.madeha.intelliinvoice.service.helper.ReturnStatusInfo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.Instant;

import static org.hibernate.query.sqm.tree.SqmNode.LOG;

/**
 * Data Mapper for Invoice objects.
 * Separates the "copying" logic (mapping) from the "processing" logic (workflow).
 * This follows the Single Responsibility Principle, keeping the ProcessingService
 * clean and focused only on the high-level pipeline.
 */
@ApplicationScoped
public class InvoiceMapper {

    @Inject
    InvoiceReturnService invoiceReturnService;

    /**
     * Converts raw AI-extracted data (POJO) into a Database Entity.
     * We transform the flat AI result into a complex JPA Entity, including
     * the calculation of return status and the mapping of individual line items.
     * * @param extractedData The raw data object returned by the AI parser.
     *
     * @param imageUrl The public S3 URL where the invoice image is stored.
     * @return A fully populated InvoiceEntity ready for database persistence.
     */

    public InvoiceEntity mapToEntity(Invoice extractedData, String imageUrl) {
        InvoiceEntity entity = new InvoiceEntity();
        entity.setImageUrl(imageUrl);
        entity.setStoreName(extractedData.getStoreName());
        entity.setTotalAmount(extractedData.getTotalAmount());
        entity.setInvoiceDate(extractedData.getInvoiceDate());
        entity.setCurrency(extractedData.getCurrency());
        entity.setVatAmount(extractedData.getVatAmount());
        entity.setInvoiceNumber(extractedData.getInvoiceNumber());
        ReturnStatusInfo returnStatusInfo = invoiceReturnService.getReturnStatusUpdate(entity);
        InvoiceStatus status = returnStatusInfo.status(); // the Status from the enum and the record after getting the data from the AI so that we
        //we can use the Invoice date and it is not null anymore
        LOG.info("Invoice processed. Days left: " + returnStatusInfo.daysLeft());
        // the left days is not saved in the database
        Instant now = Instant.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        if (extractedData.getItems() != null) {
            for (InvoiceItem itemDto : extractedData.getItems()) {
                InvoiceItemEntity itemEntity = new InvoiceItemEntity();
                itemEntity.setDescription(itemDto.getDescription());
                itemEntity.setQuantity(itemDto.getQuantity());
                itemEntity.setUnitPrice(itemDto.getUnitPrice());
                itemEntity.setLineTotal(itemDto.getLineTotal());
                itemEntity.setInvoice(entity);
                entity.getItems().add(itemEntity);
            }
        }
        return entity;
    }
}
