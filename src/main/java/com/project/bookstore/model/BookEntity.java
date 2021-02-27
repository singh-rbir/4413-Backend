package com.project.bookstore.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book", schema = "GVG91693", catalog = "")
public class BookEntity {

    @Id
    public int bid;
    public String title;
    public String author;
    public String format;
    public Double stars;
    public Double price;
    public String category;
    public String images;

    public BookEntity() {
    }

    public BookEntity(int bid, String title, String author, String format, Double stars, Double price, String category, String images) {
        this.bid = bid;
        this.title = title;
        this.author = author;
        this.format = format;
        this.stars = stars;
        this.price = price;
        this.category = category;
        this.images = images;
    }


    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Double getStars() {
        return stars;
    }

    public void setStars(Double stars) {
        this.stars = stars;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "bid=" + bid +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", format='" + format + '\'' +
                ", stars=" + stars +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", images='" + images + '\'' +
                '}';
    }
}
