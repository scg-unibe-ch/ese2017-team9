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

   @Query("select u.firstname from User u where u.userid = (select t.driver from Tour t where t.tourId=?1)")
   String findDriverFirstNameByTourId(long tourId);

   @Query("select u.lastname from User u where u.userid = (select t.driver from Tour t where t.tourId=?1)")
   String findDriverLastNameByTourId(long tourId);

   Tour findByTourId(long tourId);
   List<Tour> findAll();
   void removeByTourId(long tourId);
   List<Tour> findAllByDriverOrderByDeliverDay(long driverId);
   Tour findByTourName(String tourname);

}