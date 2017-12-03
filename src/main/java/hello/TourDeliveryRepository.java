package hello;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TourDeliveryRepository extends CrudRepository<TourDelivery, Long> {
    @Transactional
    void removeAllByTourId(long tourId);

    @Transactional
    void removeByTourIdAndDeliveryId(long tourId, long deliveryId);

    @Transactional
    TourDelivery findByTourIdAndDeliveryId(long tourId, long deliveryId);

    @Transactional
    List<TourDelivery> findByTourIdOrderByOrderId(long tourId);

    int countByTourId(long tourId);

    @Transactional
    @Query("select coalesce(max(td.orderId),0) from TourDelivery td where td.tourId =:tourId")
    long getLowestOrderIdByTourId(@Param("tourId")long tourId);

    @Modifying
    @Transactional
    @Query("update TourDelivery set orderId = :orderId where deliveryId=:deliveryId and tourId=:tourId")
    void setOrderIdbyDeliveryIdAndTourId(@Param("orderId") long orderId, @Param("deliveryId") long deliveryId, @Param("tourId") long tourId);


    @Transactional
    @Query("select tourId from TourDelivery where deliveryId=:deliveryId")
    long findTourIdByDeliveryId(@Param("deliveryId") long deliveryId);


}
