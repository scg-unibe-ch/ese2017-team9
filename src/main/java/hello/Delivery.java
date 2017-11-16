package hello;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import java.util.Date;
import java.sql.Time;

@Entity // This tells Hibernate to make a table out of this class

public class Delivery implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long deliveryId;
    private String deliveryName;
    private int width;
    private int height;
    private int depth;
    private float weight;
    private String recipient;
    @NotNull
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date deliveryWindowStart;
    @NotNull
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date deliveryWindowEnd;
    private String type;
    private String status;
    private long customer;
    private String comment;

    public long getDeliveryId() {
        return deliveryId;
    }
    public void setDeliveryId(long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getDeliveryName() {return deliveryName;}
    public void setDeliveryName(String deliveryName) {this.deliveryName = deliveryName;}

    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public int getDepth() {
        return depth;
    }
    public void setDepth(int depth) {
        this.depth = depth;
    }

    public float getWeight() { return weight; }
    public void setWeight(float weight) { this.weight = weight; }

    public String getRecipient() {return recipient;}
    public void setRecipient(String recipient) {this.recipient = recipient;}

    public Date getDeliveryWindowStart() { return deliveryWindowStart;}
    public void setDeliveryWindowStart(Date deliveryWindowStart) {this.deliveryWindowStart = deliveryWindowStart;}

    public Date getDeliveryWindowEnd() { return deliveryWindowEnd;}
    public void setDeliveryWindowEnd(Date deliveryWindowEnd) {this.deliveryWindowEnd = deliveryWindowEnd;}

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}

    public long getCustomer() {return customer;}
    public void setCustomer(long customer) {this.customer = customer;}

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Delivery(String deliveryName, int width, int height, int depth, float weight, String recipient, Date deliveryWindowStart, Date deliveryWindowEnd, String type, long customer, String comment){
        this.deliveryName = deliveryName;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.weight = weight;
        this.recipient = recipient;
        this.deliveryWindowStart = deliveryWindowStart;
        this.deliveryWindowEnd = deliveryWindowEnd;
        this.type = type;
        this.customer = customer;
        this.comment = comment;
    }

    public Delivery(long deliveryId, String deliveryName, int width, int height, int depth, float weight, String recipient, Date deliveryWindowStart, Date deliveryWindowEnd, String type, long customer, String comment){
        this.deliveryId = deliveryId;
        this.deliveryName = deliveryName;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.weight = weight;
        this.recipient = recipient;
        this.deliveryWindowStart = deliveryWindowStart;
        this.deliveryWindowEnd = deliveryWindowEnd;
        this.type = type;
        this.customer = customer;
        this.comment = comment;
    }

    public Delivery(){
        this.deliveryWindowStart = new Time(0,0,0);
        this.deliveryWindowEnd = new Time(0,0,0);
    }

}