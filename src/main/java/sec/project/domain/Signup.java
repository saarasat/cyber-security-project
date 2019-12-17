package sec.project.domain;

import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Signup extends AbstractPersistable<Long> {

    private String name;
    private String address;
    private String socialSecurity;

    public Signup() {
        super();
    }

    public Signup(String name, String address, String socialSecurity) {
        this();
        this.name = name;
        this.address = address;
        this.socialSecurity = socialSecurity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setSocialSecurity(String socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    public String getSocialSecurity() {
        return socialSecurity;
    }

}
