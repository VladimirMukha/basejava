package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class ListOrganizations extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<Organization> organizationList;

    public ListOrganizations() {
    }

    public ListOrganizations(List<Organization> organizations) {
        this.organizationList = organizations;
    }

    public ListOrganizations(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public List<Organization> getOrganizationList() {
        return organizationList;
    }

    public void setOrganizationList(List<Organization> organizationList) {
        this.organizationList = organizationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListOrganizations that = (ListOrganizations) o;
        return Objects.equals(organizationList, that.organizationList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationList);
    }

    @Override
    public String toString() {
        return "ListOrganizations{" +
                "organizationList=" + organizationList +
                '}';
    }
}