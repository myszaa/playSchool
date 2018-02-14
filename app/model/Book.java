package model;

import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Book extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String book;

    @OneToOne
    private Subject subject;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public static final Finder<Long, Book> find = new Finder<>(Book.class);

    public static List<Book> getAllBooks() {
        return Book.find.all();
    }

    public static Book findByName(String name)
    {
        Book u = Book.find.query().where().eq("book", name).findUnique();
        return u;
    }
}