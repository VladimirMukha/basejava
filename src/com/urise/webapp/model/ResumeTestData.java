package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ResumeTestData {

    public static void main(String[] args) {

        Resume resume = new Resume("Григорий", "Кислин");
        resume.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика"));
        resume.addSection(SectionType.OBJECTIVE, new ListSection(Arrays.asList("Ведущий стажировок и корпоративного обучения")));
        resume.addSection(SectionType.ACHIEVEMENT, new TextSection("Реализация c нуля Rich Internet Application"));
        resume.addSection(SectionType.QUALIFICATION, new ListSection(Arrays.asList("DB: PostgreSQL(наследование, pgplsql")));
        //  resume.addSection(SectionType.EXPERIENCE, new ListSection(Arrays.asList("Ведущий программист")));
        resume.addSection(SectionType.EDUCATION, new ListSection(Arrays.asList("Functional Programming")));
        System.out.println(resume.getMapSections());

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle() + " " + type.toString());
        }

        System.out.println("------------------------------------");
        resume.addContact(ContactType.TELEPHONE, "Тел.: +7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.HOMEPAGE, "Домашняя страница");
        resume.addContact(ContactType.GIT_HAB, "Профиль GitHub");
        resume.addContact(ContactType.LINKED_IN, "Профиль LinkedIn");
        resume.addContact(ContactType.STACK_OVER_FLOW, "Профиль Stackoverflow");

        System.out.println(resume.getMapContacts());
        for (ContactType typeContact : ContactType.values()) {
            System.out.println(typeContact.getTitle() + "  " + typeContact.toString());
        }
        System.out.println("-------------------------------------");
        Organization organization = new Organization(List.of(new Experience("организация",
                LocalDate.now(), LocalDate.of(2021, 6, 5), "описание",
                new Link("www", "homepage"))));
        System.out.println(organization.toString());

    }
}