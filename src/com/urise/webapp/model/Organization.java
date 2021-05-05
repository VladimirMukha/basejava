package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Organization extends AbstractSection {
    private List<Experience> list;

    public Organization(List<Experience> list) {
        this.list = list;
    }

    public List<Experience> getList() {
        return list;
    }

    public void setList(List<Experience> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "list=" + list +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization contact = (Organization) o;
        return Objects.equals(list, contact.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }
}