package hello;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sun.security.x509.DeltaCRLIndicatorExtension;

import javax.transaction.Transactional;
import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface DeliveryRepository extends CrudRepository<Delivery, Long> {

   Delivery findByDeliveryId(long deliveryId);
   List<Delivery> findAll();
   void removeByDeliveryId(long deliveryId);


   @Query("select d from Delivery d where d.status != 'Scheduled' and d.status != 'No Delivery Possible'")
   List<Delivery> findAllDeliveryNotScheduled();

   @Query("select d from Delivery d where d.status=:status")
   List<Delivery> findAllDeliveryByStatus(@Param("status") String status);

   @Modifying
   @Transactional
   @Query("update Delivery set status = :status where deliveryId=:deliveryId")
   void setStatusByDeliveryId(@Param("deliveryId") long deliveryId, @Param("status") String status);

}