package com.urise.webapp.model;

import java.util.Arrays;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume = new Resume("Григорий", "Кислин");
        resume.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика"));
        resume.addSection(SectionType.OBJECTIVE, new ListSection(Arrays.asList("Ведущий стажировок и корпоративного обучения")));
        resume.addSection(SectionType.ACHIEVEMENT, new TextSection("Реализация c нуля Rich Internet Application"));
        resume.addSection(SectionType.QUALIFICATION, new ListSection(Arrays.asList("DB: PostgreSQL(наследование, pgplsql")));
        resume.addSection(SectionType.EXPERIENCE, new ListSection(Arrays.asList("Ведущий программист")));
        resume.addSection(SectionType.EDUCATION, new ListSection(Arrays.asList("Functional Programming")));

        System.out.println(resume.getMapSections());

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle() + " " + type.toString());
        }
    }
}