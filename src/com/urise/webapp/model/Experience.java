package com.urise.webapp.model;

import java.util.Arrays;
import java.util.Objects;

public class Experience {
    private Link link;
    private String title;
    private String description;
    private ListDate listDateOne;
    private ListDate listDateTwo;
    private Organization organization;
    private ListDate[] arrayList;

    public Experience(String name, String url, ListDate... arrayList) {
        link = new Link(name, url);
        this.arrayList = arrayList;
    }

    public Experience(String name, String url, ListDate listDateOne, ListDate listDateTwo, Organization organization) {
        link = new Link(name, url);
        this.listDateOne = listDateOne;
        this.listDateTwo = listDateTwo;
        this.organization = organization;

    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
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

    public ListDate getListDateOne() {
        return listDateOne;
    }

    public void setListDateOne(ListDate listDateOne) {
        this.listDateOne = listDateOne;
    }

    public ListDate getListDateTwo() {
        return listDateTwo;
    }

    public void setListDateTwo(ListDate listDateTwo) {
        this.listDateTwo = listDateTwo;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public ListDate[] getArrayList() {
        return arrayList;
    }

    public void setArrayList(ListDate[] arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return Objects.equals(link, that.link) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(listDateOne, that.listDateOne) &&
                Objects.equals(listDateTwo, that.listDateTwo) &&
                Objects.equals(organization, that.organization) &&
                Arrays.equals(arrayList, that.arrayList);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(link, title, description, listDateOne, listDateTwo, organization);
        result = 31 * result + Arrays.hashCode(arrayList);
        return result;
    }

    @Override
    public String toString() {
        return "Experience{" +
                "link=" + link +
                ", listDateOne=" + listDateOne +
                ", listDateTwo=" + listDateTwo +
                ", organization=" + organization +

                '}';
    }
}