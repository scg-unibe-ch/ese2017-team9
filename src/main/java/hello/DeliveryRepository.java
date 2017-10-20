package hello;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface DeliveryRepository extends CrudRepository<Delivery, Long> {

    Delivery findByDeliveryId(Long deliveryId);
    List<Delivery> findAll();
    void removeByDeliveryId(long deliveryId);


}