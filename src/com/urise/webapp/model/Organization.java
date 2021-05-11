package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Organization extends AbstractSection {
    private List<Experience> list;
    private String name;
    private String url;

    public Organization(List<Experience> list) {
        Objects.requireNonNull(list, "list : most not be null");
        this.list = list;
    }

    public Organization(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public List<Experience> getList() {
        return list;
    }

    public void setList(List<Experience> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(list, that.list) &&
                Objects.equals(name, that.name) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "list=" + list +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}