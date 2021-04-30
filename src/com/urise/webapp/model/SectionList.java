package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class SectionList extends Section {
    private List<String> textContent;

    public SectionList(List<String> textContent) {
        this.textContent = textContent;
    }

    public List<String> getTextContent() {
        return textContent;
    }

    public void setTextContent(List<String> textContent) {
        this.textContent = textContent;
    }

    @Override
    public String toString() {
        return "SectionList{" +
                "textContent=" + textContent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionList that = (SectionList) o;
        return Objects.equals(textContent, that.textContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textContent);
    }
}