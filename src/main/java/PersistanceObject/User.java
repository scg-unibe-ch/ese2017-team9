package PersistanceObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Entity // This tells Hibernate to make a table out of this class

public class User implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long userId;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() { return password;}
    public void setPassword(String password) {this.password = password;}

    public User(User user){
        this.username = user.username;
        this.firstname = user.firstname;
        this.lastname = user.lastname;
        this.email = user.email;
        this.password = user.password;

    }

    public User(String username, String firstname, String lastname, String email, String password){
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public User(){}

    public boolean isUserAdmin(List<String> roles){
        if(!roles.isEmpty()) {
            for (int i = 0; i < roles.size(); i++) {
                if (roles.get(i).equals("ROLE_ADMIN"))
                    return true;
            }
        }
        return false;
    }

    public boolean isLogistician(List<String> roles){
        if(!roles.isEmpty()) {
            for (int i = 0; i < roles.size(); i++) {
                if (roles.get(i).equals("LOGISTICIAN"))
                    return true;
            }
        }
        return false;
    }

    public boolean isDriver(List<String> roles){
        if(!roles.isEmpty()) {
            for (int i = 0; i < roles.size(); i++) {
                if (roles.get(i).equals("DRIVER"))
                    return true;
            }
        }
        return false;
    }

}