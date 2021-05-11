package com.urise.webapp.model;

import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ResumeTestData {

    public static void main(String[] args) {

        ResumeTestData resumeTestData = new ResumeTestData();
        resumeTestData.createResumeAutomatically("Павел", "Исаков");
    }

    public void createResumeAutomatically(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика"));
        resume.addSection(SectionType.OBJECTIVE, new ListSection(Arrays.asList("Ведущий стажировок и корпоративного обучения")));
        resume.addSection(SectionType.ACHIEVEMENT, new TextSection("Реализация c нуля Rich Internet Application"));
        resume.addSection(SectionType.QUALIFICATION, new ListSection(Arrays.asList("DB: PostgreSQL(наследование, pgplsql")));
        resume.addSection(SectionType.EXPERIENCE, new ListSection(Arrays.asList("Ведущий программист")));
        resume.addSection(SectionType.EDUCATION, new ListSection(Arrays.asList("Functional Programming")));


        resume.addSection(SectionType.EDUCATION, new Organization(List.of()));
        resume.addContact(ContactType.TELEPHONE, "Тел.: +7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.HOMEPAGE, "Домашняя страница");
        resume.addContact(ContactType.GIT_HAB, "ссылка на GitHub");
        resume.addContact(ContactType.LINKED_IN, "Профиль в LinkedIn");
        resume.addContact(ContactType.STACK_OVER_FLOW, "страница на Stackoverflow");

        resume.addSection(SectionType.EDUCATION, new Organization(Collections.singletonList(new Experience(" Институт", "site",
                new ListDate(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
                new ListDate(2001, Month.MARCH, 2005, Month.JANUARY, "student", "it facultet"),
                new Organization("Organization12", "http://Organization12.ru")))));

        resume.addSection(SectionType.EDUCATION, new Organization(Arrays.asList(new Experience("Шарага", "www.googl",
                new ListDate(2011, Month.JANUARY, 2020, Month.AUGUST, "Аспирант", "заочно"),
                new ListDate(2005, Month.JANUARY, 2007, Month.FEBRUARY, "Школа", "средняя")))));

        for (SectionType sectionType : SectionType.values()) {
            System.out.println(resume.getSection(sectionType));
        }
        System.out.println("----------------------------");

        for (ContactType contactType : ContactType.values()) {
            System.out.println(resume.getContact(contactType));
        }
    }
}