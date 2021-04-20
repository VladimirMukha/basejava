package com.urise.webapp.model;

import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume>  {
    private final String uuid;
    private final String fullName;

    public Resume(String fullName) {

        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
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

    @Override
    public int compareTo(Resume o) {
        int compare = fullName.compareTo(o.fullName);
        return compare!=0?  compare :uuid.compareTo(o.uuid);
    }
}