package com.iarasantos.integrationtests.vo;

import jakarta.xml.bind.annotation.XmlRootElement;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@XmlRootElement
public class BookVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long key;
    private String author;
    private Date launchDate;
    private Double price;
    private String title;

    public BookVO() {
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookVO bookVO)) return false;

        if (!Objects.equals(key, bookVO.key)) return false;
        if (!Objects.equals(author, bookVO.author)) return false;
        if (!Objects.equals(launchDate, bookVO.launchDate)) return false;
        if (!Objects.equals(price, bookVO.price)) return false;
        return Objects.equals(title, bookVO.title);
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (launchDate != null ? launchDate.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}