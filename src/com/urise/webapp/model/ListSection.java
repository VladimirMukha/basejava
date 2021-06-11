package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class ListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<String> textContent;

    public ListSection() {
    }

    public ListSection(List<String> textContent) {
        this.textContent = textContent;
    }

    public ListSection(String... textContent) {
        this(Arrays.asList(textContent));
    }

    public List<String> getTextContent() {
        return textContent;
    }

    public void setTextContent(List<String> textContent) {
        this.textContent = textContent;
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "textContent=" + textContent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(textContent, that.textContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textContent);
    }
}