package hello;

import javax.persistence.*;

@Entity
@Table(name = "tour_delivery")
public class TourDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long tourDeliveryId;
    private long tourId;
    private long deliveryId;
    private long orderId;


    public long getTourDeliveryId() {
        return tourDeliveryId;
    }
    public void setTourDeliveryId(long tourDeliveryId) {
        this.tourDeliveryId = tourDeliveryId;
    }

    public long getTourId() {
        return tourId;
    }
    public void setTourId(long tourId) {
        this.tourId = tourId;
    }

    public long getDeliveryId() {
        return deliveryId;
    }
    public void setDeliveryId (long deliveryId){
        this.deliveryId = deliveryId;
    }

    public long getOrder() {return orderId;}
    public void setOrder(long orderId) {this.orderId = orderId;}

    public TourDelivery(long tourId, long deliveryId, long orderId){
        this.tourId = tourId;
        this.deliveryId = deliveryId;
        this.orderId = orderId;
    }

    public TourDelivery(){}
}
