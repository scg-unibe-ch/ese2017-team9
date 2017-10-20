package hello;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
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
    UserRole findByTourIdAndDeliveryId(long tourId, long deliveryId);

    @Transactional
    List<UserRole> findByTourId(long tourId);

}
