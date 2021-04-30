package com.urise.webapp.model;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Resume {
    private final String uuid;
    private final String fullName;
    public Map<SectionType, Section> mapSections;
    public Map<ContactType, String> mapContacts;

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

    @Override
    public String toString() {
        return fullName + " " + uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        if (!resume.fullName.equals(fullName)) return false;
        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName);
    }
}