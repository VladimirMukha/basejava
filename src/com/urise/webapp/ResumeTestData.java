package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.time.Month;

public class ResumeTestData {

    public static void main(String[] args) {
        ResumeTestData.createResumeAutomatically("Павел", "Исаков");
    }

    public static Resume createResumeAutomatically(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика"));
        resume.addSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения"));
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection("Реализация c нуля Rich Internet Application."));
        resume.addSection(SectionType.QUALIFICATION, new ListSection("DB: PostgreSQL наследование, pgplsql."));

        resume.addContact(ContactType.TELEPHONE, "Тел.: +7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.HOMEPAGE, "Домашняя страница");
        resume.addContact(ContactType.GIT_HAB, "ссылка на GitHub");
        resume.addContact(ContactType.LINKED_IN, "Профиль в LinkedIn");
        resume.addContact(ContactType.STACK_OVER_FLOW, "страница на Stackoverflow");

        resume.addSection(SectionType.EDUCATION, new ListOrganizations(new Organization("Мгу", "mgu@yahoo.com",
                new Organization.Experience(DateUtil.of(2007, Month.JANUARY), DateUtil.of(2005, Month.DECEMBER)
                        , "Аспирант.", "заочно.")),
                new Organization("RIT Center", null, new Organization.Experience(DateUtil.of(2012, Month.APRIL),
                        DateUtil.of(2014, Month.OCTOBER), "Java архитектор.",
                        "Организация процесса."))));

        resume.addSection(SectionType.EXPERIENCE, new ListOrganizations(new Organization("NASA", "nasa@mail",
                new Organization.Experience(DateUtil.of(1967, Month.DECEMBER), DateUtil.of(
                        2005, Month.AUGUST), "Декан.", "обучение.")),
                new Organization("STO Center", "sobaca@net", new Organization.Experience(DateUtil.of(2015, Month.APRIL),
                        DateUtil.of(2016, Month.NOVEMBER), "Java devops.",
                        "Senior."))));
        return resume;
    }
}