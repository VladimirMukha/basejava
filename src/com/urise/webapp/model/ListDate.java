package com.urise.webapp.model;

import java.time.Month;

public class ListDate {
    int startYear;
    Month startMonth;
    int endYear;
    Month endMonth;
    String title;
    String description;

    public ListDate(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
        this.startYear = startYear;
        this.startMonth = startMonth;
        this.endYear = endYear;
        this.endMonth = endMonth;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "ListDate{" +
                "startYear=" + startYear +
                ", startMonth=" + startMonth +
                ", endYear=" + endYear +
                ", endMonth=" + endMonth +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}