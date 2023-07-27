package com.example.crudcomic.models;

import java.io.Serializable;
import java.util.List;

public class Comic  implements Serializable {
    private String _id;
    private String title;
    private String description;
    private String author;
    private String year;
    private String coverImage;
    private List images;

    public Comic() {
    }

    public Comic(String title, String description, String author, String year, String coverImage, List images) {

        this.title = title;
        this.description = description;
        this.author = author;
        this.year = year;
        this.coverImage = coverImage;
        this.images = images;
    }

    public Comic( String title, String description, String author, String year, String coverImage) {

        this.title = title;
        this.description = description;
        this.author = author;
        this.year = year;
        this.coverImage = coverImage;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public List getImages() {
        return images;
    }

    public void setImages(List images) {
        this.images = images;
    }
}
