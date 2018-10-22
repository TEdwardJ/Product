package com.study.entity;



import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

public class Product {

    private String picturePath;

    private String name;

    private double price;

    private int id;

    private LocalDateTime addDate;

    public Product(int id,String picturePath, String name, double price, LocalDateTime addDate) {
        this.id = id;
        this.picturePath = picturePath;
        this.name = name;
        this.price = price;
        this.addDate = addDate;
    }

    public Product(String picturePath, String name, double price, LocalDateTime addDate) {
        this.picturePath = picturePath;
        this.name = name;
        this.price = price;
        this.addDate = addDate;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDateTime addDate) {
        this.addDate = addDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return getId() == product.getId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}
