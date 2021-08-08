package com.urise.webapp.model;

import java.util.Objects;

public class TextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    public static final TextSection EMPTY = new TextSection("");
    private String text;

    public TextSection() {
    }

    public TextSection(String text) {
        Objects.requireNonNull(text, "most not be null");
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}