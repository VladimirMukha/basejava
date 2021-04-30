package com.urise.webapp.model;

import java.time.LocalDate;

public class ContactInformation {
    private String title;
    private LocalDate date;
    private String description;
    private Link link;

    public ContactInformation(String title, LocalDate date, String description, Link link) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }
}
