package com.example.workfuel.objects;

public class PopularDishes {
    String name;
    String description;
    String rating;
    String img;

    public PopularDishes(String dish, String description, String rating, String img) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.img = img;
    }
    public PopularDishes() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
