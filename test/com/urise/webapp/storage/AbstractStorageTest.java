package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {

    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, "name_1");
        RESUME_2 = new Resume(UUID_2, "name_2");
        RESUME_3 = new Resume(UUID_3, "name_3");
        RESUME_4 = new Resume(UUID_4, "name_4");
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);

        RESUME_1.addContact(ContactType.EMAIL, "mail@");
        RESUME_1.addContact(ContactType.TELEPHONE, "5279578");
        RESUME_1.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика"));
        RESUME_1.addSection(SectionType.OBJECTIVE, new ListSection(Arrays.asList("Ведущий стажировок и корпоративного обучения")));
        RESUME_1.addSection(SectionType.ACHIEVEMENT, new TextSection("Реализация c нуля Rich Internet Application"));
        RESUME_1.addSection(SectionType.QUALIFICATION, new ListSection(Arrays.asList("DB: PostgreSQL(наследование, pgplsql")));
        RESUME_1.addSection(SectionType.EDUCATION, new ListSection(Arrays.asList("Functional Programming")));

        RESUME_1.addSection(SectionType.EDUCATION, new Organization(Collections.singletonList(new Experience("Institute", null,
                new ListDate(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
                new ListDate(2001, Month.MARCH, 2005, Month.JANUARY, "student", "it facultet"),
                new Organization("Organization12", "http://Organization12.ru")))));
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        Resume resume = new Resume(UUID_4, "new name");
        storage.save(resume);
        Assert.assertEquals(resume, storage.get(UUID_4));
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1, "new Resume");
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume resume = new Resume(UUID_4);
        storage.update(resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExits() {
        storage.delete(UUID_4);
    }

    @Test
    public void get() {
        Assert.assertEquals(RESUME_1, storage.get(UUID_1));
        Assert.assertEquals(RESUME_2, storage.get(UUID_2));
        Assert.assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_4);
    }

    @Test
    public void getAllSorted() {
        List<Resume> result = new ArrayList<>();
        result.add(RESUME_1);
        result.add(RESUME_2);
        result.add(RESUME_3);
        assertEquals(result, storage.getAllSorted());
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }
}