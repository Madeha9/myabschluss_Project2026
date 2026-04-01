package at.madeha.intelliinvoice.database;
/* Jakarta enterprise for CDI (Contexts and Dependency Injection)  dependency injection , very important in quarkus
 * CDI context and dependency injection means the framework Quarkus create the object and inject  them when needed */

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
//@ApplicationScoped  means this class use quarkus injection method , generating the entities

/**
 * Repository class for managing {@link InvoiceEntity} persistence.
 * Uses Jakarta Persistence (JPA) and CDI for database operations.
 */
@ApplicationScoped
public class InvoiceRepository {
    //Entity manger is the database tool to create query and save, find all ect, it is final
    private final EntityManager em;
    /*
    CDI in quarkus will create the object and inject everywhere so ı do not create the entity manager by myself
    when the attribute  is final  ,it does need always direct assign like x=6; or constructor
     */

    /** * Constructor injection: CDI (Contexts and Dependency Injection)
     * automatically provides the EntityManager so we don't create it manually.
     */
    public InvoiceRepository(EntityManager em) {
        this.em = em;
    }
    //for database @Transactional transactions save all or nothing will be saved
    //em replace the conn.save in the traditional database

    /**
     * Saves or updates an invoice in the database.
     *
     * @param invoice The entity to persist(save).
     * @return The managed entity.
     */
    @Transactional
    public InvoiceEntity save(InvoiceEntity invoice) {
        //if there is no matched ID , means is new invoice we can add it to the database
        if (invoice.getId() == null) {
            //persist = insert method
            em.persist(invoice);
            return invoice;
            //give us the saved invoice back
        }
        return em.merge(invoice);
        //merge = update method or add new id not exist
    }

    //use optional because we are not sure if the result is exiting

    /** @return An Optional containing the invoice if found by its UUID. */
    public Optional<InvoiceEntity> findById(UUID id) {
        return Optional.ofNullable(em.find(InvoiceEntity.class, id));
    }

    /**
     * Retrieves all invoices including their items.
     * Uses JOIN FETCH to solve the N+1 problem by loading items in a single query.
     */
    public List<InvoiceEntity> findAll() {
        /* * Why we use 'LEFT JOIN FETCH' and 'DISTINCT':
         * 1. Efficiency: Usually, Hibernate makes 1 trip for the invoice + 1 trip for EVERY item (N+1 problem).
         * 'FETCH JOIN' glues the items to the invoice immediately so we only make 1 trip.
         * 2. Safety: 'LEFT JOIN' ensures that even if an invoice has NO items yet, it still shows up.
         * 3. No Duplicates: 'DISTINCT' makes sure we don't get the same invoice 5 times just because
         * it has 5 different items.
         */
        return em.createQuery(
                "SELECT DISTINCT i FROM InvoiceEntity i LEFT JOIN FETCH i.items",
                InvoiceEntity.class
        ).getResultList();
    }

    /**
     * Searches for invoices by store name (case-insensitive).
     * @param name The search string.
     */
    public List<InvoiceEntity> findByStoreName(String name) {
        return em.createQuery("SELECT i FROM InvoiceEntity i WHERE LOWER(i.storeName) LIKE LOWER(:name)", InvoiceEntity.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    /** Deletes an invoice and its associated items from the database. */
    @Transactional
    public void deleteById(UUID id) {
        InvoiceEntity invoice = em.find(InvoiceEntity.class, id);

        if (invoice == null) {
            throw new IllegalArgumentException("Invoice not found");
        }

        em.remove(invoice);
    }

    /** * Utility method to clear the database.
     * Loops through entities to ensure JPA Cascades and Orphan Removal are triggered.
     */
    @Transactional
    public void deleteAll() {
        List<InvoiceEntity> invoices = findAll();
        for (InvoiceEntity invoice : invoices) {
            em.remove(invoice);
        }
    }
}


