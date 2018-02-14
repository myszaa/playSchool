package model;

import be.objectify.deadbolt.java.models.Role;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.util.List;


@Entity
public class Subject extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String subject;

    @OneToOne
    private Book book;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public static final Finder<Long, Subject> find = new Finder<>(Subject.class);

    public static List<Subject> getAllSubjects() {
        return Subject.find.all();
    }

    public static Subject findByName(String name)
    {
        Subject u = Subject.find.query().where().eq("subject", name).findUnique();
        return u;
    }
}
