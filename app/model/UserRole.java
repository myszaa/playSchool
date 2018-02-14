package model;

import be.objectify.deadbolt.java.models.Role;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import java.util.Objects;

@Entity
public class UserRole extends Model implements Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String role;

    @Override
    public String getName() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static final Finder<Long, UserRole> find = new Finder<>(UserRole.class);

    public static List<UserRole> getAllUserRole() {
        return UserRole.find.all();
    }

    public static UserRole findByUserRole(String ur)
    {
        return UserRole.find.query().where().eq("role", ur).findUnique();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(role, userRole.role);
    }

    @Override
    public int hashCode() {

        return Objects.hash(role);
    }
}