package Repository;

import PersistanceObject.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByCustomerId(Long customerId);
    List<Customer> findAll();
    void removeByCustomerId(long customerId);


}