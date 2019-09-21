package com.check24.codingchallenge.bookstore.payload;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Bassem
 */
public class BookDto extends AudiModelDto{

    private long id;

    private String name;

    private String details;

    private double price;

    private String image;

    private Set<UserDto> viewedBy = new HashSet<>();;

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

    public Set<UserDto> getViewedBy() {
        return viewedBy;
    }

    public void setViewedBy(Set<UserDto> viewedBy) {
        this.viewedBy = viewedBy;
    }



    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}
