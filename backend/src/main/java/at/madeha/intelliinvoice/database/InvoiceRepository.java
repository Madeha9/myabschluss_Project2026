package at.madeha.intelliinvoice.database;
/* Jakarta enterprise for CDI (Contexts and Dependency Injection)  dependency injection , very important in quarkus
 * CDI context and dependency injection means the framework Quarkus create the object and inject  them when needed */

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//ApplicationScoped this class use quarkus injection method , generating the entities
@ApplicationScoped
public class InvoiceRepository {
    //Entity manger is the database tool to create query and save, find all ect, it is final
    private final EntityManager em;

    /*
    CDI in quarkus will create the object and inject everywhere so ı do not create the entity manager be m self
    when the attribute final is ,it does need always direct assign or constructor
     */
    public InvoiceRepository(EntityManager em) {
        this.em = em;
    }

    //for database @Transactional transactions save all or nothing will be saved
    //em replace the conn.save in the traditional database
    @Transactional
    public InvoiceEntity save(InvoiceEntity invoice) {
        //if there is no matched ID , means is new invoice we can add it to the databse
        if (invoice.getId() == null) {
            //persist = insert method
            em.persist(invoice);
            return invoice;
            //give us the saved invoice back
        }
        return em.merge(invoice);
        //merge = update method
    }

    //use optional because we are not sure if the result is exiting
    public Optional<InvoiceEntity> findById(UUID id) {
        return Optional.ofNullable(em.find(InvoiceEntity.class, id));
    }

    public List<InvoiceEntity> findAll() {
        // Standard JPA query using JOIN FETCH to load the items collection eagerly
        /* using the LEFT JOIN FETCH to bring the items even if the list is empty, and load them into memory
         * we use the Distinct to avoid duplication from the join
         * this to fetch all  the invoice in one singe database trip
         * fetch - join : glues items to the invoices immediately reduce (N+1 issues)
         */
        return em.createQuery(
                "SELECT DISTINCT i FROM InvoiceEntity i LEFT JOIN FETCH i.items",
                InvoiceEntity.class
        ).getResultList();
    }

    /**
     * Search by Store Name.
     * to search the invoice using the store name
     */
    public List<InvoiceEntity> findByStoreName(String name) {
        return em.createQuery("SELECT i FROM InvoiceEntity i WHERE LOWER(i.storeName) LIKE LOWER(:name)", InvoiceEntity.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }


    @Transactional
    public void deleteById(UUID id) {
        InvoiceEntity invoice = em.find(InvoiceEntity.class, id);

        if (invoice == null) {
            throw new IllegalArgumentException("Invoice not found");
        }

        em.remove(invoice);
    }

    //just tı clean the database after eah test
    @Transactional
    public void deleteAll() {
//  If you want to respect your CascadeType.ALL, delete objects one by one
        List<InvoiceEntity> invoices = findAll();
        for (InvoiceEntity invoice : invoices) {
            em.remove(invoice);
        }
    }
}


