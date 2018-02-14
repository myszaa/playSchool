package model;

import be.objectify.deadbolt.java.models.Permission;
import be.objectify.deadbolt.java.models.Role;
import be.objectify.deadbolt.java.models.Subject;
import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="users")
public class User extends Model implements Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Constraints.Required(message="error.field.required")
    private String username;
    @Constraints.Required(message="error.field.required")
    private String firstname;
    @Constraints.Required(message="error.field.required")
    private String lastname;
    @Constraints.Required(message="error.field.required")
    @Constraints.Email(message="error.email.invalid")
    private String email;
    @Constraints.Required(message="error.field.required")
    private String password;
    @Constraints.Required(message="error.field.required")
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

    public static User findByAutenticationData(String login, String password) {
        User u = User.find.query().where().eq("username", login).eq("password", password).findUnique();
        if (u != null ) return u;
        else return null;
    }

    public static User findByUsername(String username)
    {
        User u = User.find.query().where().eq("username", username).findUnique();
        return u;
    }

    @Override
    public List<? extends Role> getRoles() {
        return null;
    }

    @Override
    public List<? extends Permission> getPermissions() {
        return new ArrayList<>();
    }

    @Override
    public String getIdentifier() {
        return username;
    }
}
