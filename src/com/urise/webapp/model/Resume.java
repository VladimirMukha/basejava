package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Resume implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Resume EMPTY = new Resume();
    private String uuid;
    private String fullName;
    private final Map<SectionType, AbstractSection> mapSections = new EnumMap<>(SectionType.class);
    private final Map<ContactType, String> mapContacts = new EnumMap<>(ContactType.class);

    static {
        EMPTY.addSection(SectionType.OBJECTIVE, TextSection.EMPTY);
        EMPTY.addSection(SectionType.PERSONAL, TextSection.EMPTY);
        EMPTY.addSection(SectionType.ACHIEVEMENT, ListSection.EMPTY);
        EMPTY.addSection(SectionType.QUALIFICATION, ListSection.EMPTY);
        EMPTY.addSection(SectionType.EXPERIENCE, new ListOrganizations(Organization.EMPTY));
        EMPTY.addSection(SectionType.EDUCATION, new ListOrganizations(Organization.EMPTY));
    }

    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid most not be null");
        Objects.requireNonNull(fullName, "fullName most not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Map<SectionType, AbstractSection> getMapSections() {
        return mapSections;
    }

    public Map<ContactType, String> getMapContacts() {
        return mapContacts;
    }

    public String getContact(ContactType contactType) {
        return mapContacts.get(contactType);
    }

    public AbstractSection getSection(SectionType sectionType) {
        return mapSections.get(sectionType);
    }

    public void addContact(ContactType contactType, String contact) {
        mapContacts.put(contactType, contact);
    }

    public void addSection(SectionType sectionType, AbstractSection section) {
        mapSections.put(sectionType, section);
    }

    @Override
    public String toString() {
        return uuid + " " +
                fullName + " " + mapSections + " " + mapContacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(mapSections, resume.mapSections) &&
                Objects.equals(mapContacts, resume.mapContacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, mapSections, mapContacts);
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}