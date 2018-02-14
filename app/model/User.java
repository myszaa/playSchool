package model;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="users")
public class User extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private boolean enabled;
    private String activationtoken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getActivationtoken() {
        return activationtoken;
    }

    public void setActivationtoken(String activationtoken) {
        this.activationtoken = activationtoken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return enabled == user.enabled &&
                Objects.equals(username, user.username) &&
                Objects.equals(firstname, user.firstname) &&
                Objects.equals(lastname, user.lastname) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(activationtoken, user.activationtoken);
    }

    @Override
    public int hashCode() {

        return Objects.hash(username, firstname, lastname, email, password, enabled, activationtoken);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", activationtoken='" + activationtoken + '\'' +
                '}';
    }

    public static final Finder<Long, User> find = new Finder<>(User.class);

    public static User findByActivationtoken(String code) {
        return User.find.query().where().eq("activationtoken", code).findUnique();
    }
}
