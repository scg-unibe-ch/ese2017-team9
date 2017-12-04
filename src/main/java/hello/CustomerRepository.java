package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    @Query("select d.deliveryId from Delivery d, Customer c where ?1 in (d.customer)")
    List<String> customerListErasable(long customerId);


    Customer findByCustomerId(Long customerId);
    List<Customer> findAll();
    void removeByCustomerId(long customerId);


}