package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.aspectj.bridge.Version.getTime;

@Entity // This tells Hibernate to make a table out of this class

public class Tour implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long tourId;
    private String tourName;
    private Date deliverDay;
    private Time estimatedTime;
    private Time usedTime;
    private long driver;


    public Long getTourId() { return tourId; }
    public void setTourId(Long tourId) { this.tourId = tourId; }

    public String getTourName() {return tourName;}
    public void setTourName(String tourName) {this.tourName = tourName;}

    public Date getDeliverDay() { return deliverDay; }
    public void setDeliverDay(Date deliverDay) { this.deliverDay = deliverDay; }

    public Time getEstimatedTime() { return estimatedTime;}
    public void setEstimatedTime(Time estimatedTime) {this.estimatedTime = estimatedTime;}

    public Time getUsedTime() { return usedTime;}
    public void setUsedTime(Time usedTime) {this.usedTime = usedTime;}

    public long getDriver() {return driver;}
    public void setDriver(long driver) {this.driver = driver;}
    

    public Tour(String tourName, Date deliveryDay, Time estimatedTime, Time usedTime, long driver){
        this.tourName = tourName;
        this.deliverDay = deliveryDay;
        this.estimatedTime = estimatedTime;
        this.usedTime = usedTime;
        this.driver = driver;
    }

    public Tour(){
        deliverDay = new java.sql.Date(getTime());
        Time time = new Time(0);
        estimatedTime = time;
        usedTime = time;

    }

}