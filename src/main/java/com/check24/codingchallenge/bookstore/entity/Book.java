package com.check24.codingchallenge.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Bassem
 */
@Entity
@Table(name = Book.TABLE_NAME)
public class Book extends AuditModel {
    static final String TABLE_NAME = "BOOK";

    @Id
    @Column(name = "BOOK_ID", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "DETAILS")
    private String details;

    @NotNull
    @Column(name = "PRICE")
    private double price;

    @Column(name = "IMAGE")
    private String image;

    @ManyToMany
    @JoinTable(
            name = "USER_VIEW",
            joinColumns = @JoinColumn(name = "BOOK_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    @JsonManagedReference
    private Set<User> viewedBy = new HashSet<>();


    @OneToMany(
            mappedBy = "firstBook",
            cascade = CascadeType.ALL
    )
    @Fetch(value = FetchMode.SUBSELECT)
    private Set<BookSimilarity> similarTo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<User> getViewedBy() {
        return viewedBy;
    }

    public void addViewBy(User user) {
        viewedBy.add(user);
        user.getViewedBooks().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}