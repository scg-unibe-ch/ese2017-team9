package hello;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.sql.Time;
import java.util.List;

import static org.aspectj.bridge.Version.getTime;

@Entity // This tells Hibernate to make a table out of this class

public class Tour implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long tourId;
    private String tourName;
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private Date deliverDay;
    @NotNull
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date estimatedTime;
    @NotNull
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "HH:mm")
    private Date usedTime;
    private long driver;


    public Long getTourId() { return tourId; }
    public void setTourId(Long tourId) { this.tourId = tourId; }

    public String getTourName() {return tourName;}
    public void setTourName(String tourName) {this.tourName = tourName;}

    public Date getDeliverDay() { return deliverDay; }
    public void setDeliverDay(Date deliverDay) { this.deliverDay = deliverDay; }

    public Date getEstimatedTime() { return estimatedTime;}
    public void setEstimatedTime(Date estimatedTime) {this.estimatedTime = estimatedTime;}

    public Date getUsedTime() { return usedTime;}
    public void setUsedTime(Date usedTime) {this.usedTime = usedTime;}

    public long getDriver() {return driver;}
    public void setDriver(long driver) {this.driver = driver;}
    

    public Tour(String tourName, Date deliveryDay, Date estimatedTime, Date usedTime, long driver){
        this.tourName = tourName;
        this.deliverDay = deliveryDay;
        this.estimatedTime = estimatedTime;
        this.usedTime = usedTime;
        this.driver = driver;
    }

    public Tour(){
        this.deliverDay = new Date(0);
        this.estimatedTime = new Time(0,0,0);
        this.usedTime = new Time(0,0,0);;

    }

}