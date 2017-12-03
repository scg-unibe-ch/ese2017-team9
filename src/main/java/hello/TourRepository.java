package hello;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface TourRepository extends CrudRepository<Tour, Long> {
   @Query("select d.deliveryName from Delivery d, TourDelivery td where d.deliveryId in (select td.deliveryId from TourDelivery where td.tourId=?1)")
   List<String> findDeliveryNameByTourId(long tourId);

   Tour findByTourId(long tourId);
   List<Tour> findAll();
   void removeByTourId(long tourId);
   List<Tour> findAllByDriver(long driverId);
   Tour findByTourName(String tourname);
}