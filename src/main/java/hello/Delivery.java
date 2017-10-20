package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity // This tells Hibernate to make a table out of this class

public class Delivery implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long deliveryId;
    private String deliveryName;
    private float width;
    private float height;
    private float depth;
    private float weight;
    private Date deliveryWindowStart;
    private Date deliveryWindowEnd;
    private String type;
    private long customer;
    private String comment;

    public Long getDeliveryId() {
        return deliveryId;
    }
    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getDeliveryName() {return deliveryName;}
    public void setDeliveryName(String deliveryName) {this.deliveryName = deliveryName;}

    public float getWidth() {
        return width;
    }
    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }
    public void setHeight(float height) {
        this.height = height;
    }

    public float getDepth() {
        return depth;
    }
    public void setDepth(float depth) {
        this.depth = depth;
    }

    public float getWeight() { return weight; }
    public void setWeight(float weight) { this.weight = weight; }

    public Date getDeliveryWindowStart() { return deliveryWindowStart;}
    public void setDeliveryWindowStart(Date deliveryWindowStart) {this.deliveryWindowStart = deliveryWindowStart;}

    public Date getDeliveryWindowEnd() { return deliveryWindowEnd;}
    public void setDeliveryWindowEnd(Date deliveryWindowEnd) {this.deliveryWindowEnd = deliveryWindowEnd;}

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    public long getCustomer() {return customer;}
    public void setCustomer(long customer) {this.customer = customer;}

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Delivery(String deliveryName, float width, float height, float depth, float weight, Date deliveryWindowStart, Date deliveryWindowEnd, String type, long customer, String comment){
        this.deliveryName = deliveryName;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.weight = weight;
        this.deliveryWindowStart = deliveryWindowStart;
        this.deliveryWindowEnd = deliveryWindowEnd;
        this.type = type;
        this.customer = customer;
        this.comment = comment;
    }

    public Delivery(){}

}