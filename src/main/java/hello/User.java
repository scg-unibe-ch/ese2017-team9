package hello;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity // This tells Hibernate to make a table out of this class

public class User implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long userid;
    @Column(unique=true)
    @NotNull
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private boolean locked;
    public boolean deleting;


    public long getUserid() {
        return userid;
    }
    public void setUserid(long userid) {
        this.userid = userid;
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

    public boolean getLocked() {
        return locked;
    }
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean getIsUserErasable() {
        return deleting;
    }
    public void setIsUserErasable(boolean deleting) {
        this.deleting = deleting;
    }

    public User(User user){
        this.username = user.username;
        this.firstname = user.firstname;
        this.lastname = user.lastname;
        this.email = user.email;
        this.password = user.password;
        this.locked = user.locked;
    }

    public User(String username, String firstname, String lastname, String email, String password, boolean locked){
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.locked = locked;
    }

    public User(){}

}