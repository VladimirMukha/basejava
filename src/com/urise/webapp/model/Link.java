package com.urise.webapp.model;

import java.util.Objects;

public class Link {

    String url;
    String text;

    public Link(String url, String type) {
        this.url = url;
        this.text = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(url, link.url) &&
                Objects.equals(text, link.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, text);
    }
}