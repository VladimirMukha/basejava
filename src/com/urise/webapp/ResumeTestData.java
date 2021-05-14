package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

public class ResumeTestData {

    public static void main(String[] args) {
        ResumeTestData.createResumeAutomatically("Павел", "Исаков");
    }

    public static Resume createResumeAutomatically(String uuid, String fullName) {
        var resume = new Resume(uuid, fullName);

        resume.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика"));
        resume.addSection(SectionType.OBJECTIVE, new ListSection(Arrays.asList("Ведущий стажировок и корпоративного обучения")));
        resume.addSection(SectionType.ACHIEVEMENT, new TextSection("Реализация c нуля Rich Internet Application"));
        resume.addSection(SectionType.QUALIFICATION, new ListSection(Arrays.asList("DB: PostgreSQL(наследование, pgplsql")));
        resume.addSection(SectionType.EXPERIENCE, new ListSection(Arrays.asList("Ведущий программист")));
        resume.addSection(SectionType.EDUCATION, new ListSection(Arrays.asList("Functional Programming")));

        resume.addContact(ContactType.TELEPHONE, "Тел.: +7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.HOMEPAGE, "Домашняя страница");
        resume.addContact(ContactType.GIT_HAB, "ссылка на GitHub");
        resume.addContact(ContactType.LINKED_IN, "Профиль в LinkedIn");
        resume.addContact(ContactType.STACK_OVER_FLOW, "страница на Stackoverflow");

        resume.addSection(SectionType.EDUCATION, new ListOrganizations(new Organization("Name", "URL",
                new Experience(LocalDate.of(1967, Month.JANUARY, 5), LocalDate.of(
                        2005, Month.DECEMBER, 16), "Аспирант", "заочно")),
                new Organization("RIT Center", null, new Experience(LocalDate.of(2012, Month.APRIL, 5),
                        LocalDate.of(2014, Month.OCTOBER, 1), "Java архитектор",
                        "Организация процесса"))));

        resume.addSection(SectionType.EDUCATION, new ListOrganizations(new Organization("Name", "URL",
                new Experience(LocalDate.of(1967, Month.JANUARY, 5), LocalDate.of(
                        2005, Month.DECEMBER, 16), "Аспирант", "заочно")),
                new Organization("RIT Center", null, new Experience(LocalDate.of(2012, Month.APRIL, 5),
                        LocalDate.of(2014, Month.OCTOBER, 1), "Java архитектор",
                        "Организация процесса"))));

        for (SectionType sectionType : SectionType.values()) {
            System.out.println(resume.getSection(sectionType));
        }
        System.out.println("----------------------------");

        for (ContactType contactType : ContactType.values()) {
            System.out.println(resume.getContact(contactType));
        }
        return resume;
    }
}