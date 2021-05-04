package com.urise.webapp.model;

import javax.swing.*;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Resume {
    private final String uuid;
    private final String fullName;
    private final Map<SectionType, AbstractSection> mapSections = new EnumMap<>(SectionType.class);
    private final Map<ContactType, String> mapContacts = new EnumMap<>(ContactType.class);

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
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                ", mapSections=" + mapSections +
                ", mapContacts=" + mapContacts +
                '}';
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
}