package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Entity // This tells Hibernate to make a table out of this class

public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long customerId;
    private String company;
    private String contact;
    private String street;
    private String city;
    private String phone;
    private String email;
    private boolean deleting;

    public boolean getIsCustomerErasable(){return deleting;}

    public void setIsCustomerErasable(boolean deleting){this.deleting = deleting;}


    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public Customer(String company, String contact, String email, String street, String city, String phone) {
        this.company = company;
        this.contact = contact;
        this.street = street;
        this.city = city;
        this.email = email;
        this.phone = phone;
    }

    public Customer() {
    }

}