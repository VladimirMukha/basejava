package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected static File storageDir = Config.getInstance().getStorageDir();
    protected Storage storage;

    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final String UUID_4 = UUID.randomUUID().toString();

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, "name_1");
        RESUME_2 = new Resume(UUID_2, "name_2");
        RESUME_3 = new Resume(UUID_3, "name_3");
        RESUME_4 = new Resume(UUID_4, "name_4");

        RESUME_1.addContact(ContactType.TELEPHONE,"5246");
        RESUME_1.addContact(ContactType.HOMEPAGE,"homepage.ru");

        RESUME_4.addContact(ContactType.SKYPE,"skype");
        RESUME_4.addContact(ContactType.EMAIL,"@mail.ru");
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
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void save() {
        Resume resume = new Resume(UUID_4, "new name");
        storage.save(resume);
        assertGet(resume);
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1, "new Resume");
        newResume.addContact(ContactType.TELEPHONE,"534");
        newResume.addContact(ContactType.SKYPE,"skype");
        storage.update(newResume);
        assertGet(newResume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume resume = new Resume(UUID_4);
        storage.update(resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExits() {
        storage.delete(UUID_4);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_4);
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(Arrays.asList(RESUME_1, RESUME_2, RESUME_3), list);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    public void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    public void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}