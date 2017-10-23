package hello;

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
public interface DeliveryRepository extends CrudRepository<Delivery, Long> {

   Delivery findByDeliveryId(long deliveryId);
   List<Delivery> findAll();
   void removeByDeliveryId(long deliveryId);


   //@Query(value = "select del from Delivery del left outer join TourDelivery td on del.deliveryId = td.deliveryId", nativeQuery = true)
   //List<Delivery> findAllDeliveryNotScheduled();

}