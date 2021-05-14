package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization {
    private List<Experience> list;
    private final Link homePage;

    public Organization(Link link, List<Experience> list) {
        Objects.requireNonNull(list, "list : most not be null");
        this.homePage = link;
        this.list = list;
    }

    public Organization(String name, String url, Experience... lists) {
        this(new Link(name, url), Arrays.asList(lists));
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
                Objects.equals(homePage, that.homePage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }

    @Override
    public String toString() {
        return "Organization {  homePage" + homePage +
                "list=" + list +
                " }";
    }
}