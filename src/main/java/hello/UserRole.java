package hello;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userroleid;
    private long userid;
    private String role;


    public long getId() {
        return userroleid;
    }

    public void setId(long userroleid) {
        this.userroleid = userroleid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid (long userid){
        this.userid = userid;
    }
}
