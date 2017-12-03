package hello;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Time;
import java.util.List;


@Entity // This tells Hibernate to make a table out of this class

public class Tour implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long tourId;
    private String tourName;
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
    //private List<String> deliveriesFromTour;
    private String deliveriesInTour;

    public long getTourId() { return tourId; }
    public void setTourId(long tourId) { this.tourId = tourId; }

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

    /*public List<String> getDeliveriesInTour() {return deliveriesFromTour;}
    public void setDeliveriesInTour(List<String> deliveriesFromTour) {this.deliveriesFromTour = deliveriesFromTour;}*/

    public List<String> getDeliveriesInTour() {
        List<String> delsTour = new ArrayList<>();

        String[] ary = this.deliveriesInTour.split("#%");
        int j=0;
        while(j<ary.length){
            delsTour.add(ary[j]);
            j++;
        }
        return delsTour;
    }
    public void setDeliveriesInTour(String deliveriesInTour) {this.deliveriesInTour = deliveriesInTour;}

    

    public Tour(String tourName, Date deliveryDay, Date estimatedTime, Date usedTime, long driver){
        this.tourName = tourName;
        this.deliverDay = deliveryDay;
        this.estimatedTime = estimatedTime;
        this.usedTime = usedTime;
        this.driver = driver;
    }

    public Tour(){
        this.deliverDay = new Date();
        this.estimatedTime = new Time(0,0,0);
        this.usedTime = new Time(0,0,0);;

    }

}