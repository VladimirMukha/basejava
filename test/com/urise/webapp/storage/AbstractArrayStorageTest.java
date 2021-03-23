package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        Resume resume = new Resume("uuid_4");
        storage.save(resume);
        Assert.assertEquals(resume, storage.get("uuid_4"));
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID_1));
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1);
        storage.update(newResume);
        Assert.assertEquals(newResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.get("new Resume");
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        Resume[] listResume = storage.getAll();
        Assert.assertArrayEquals(listResume, storage.getAll());
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExits() {
        storage.delete("new Resume");
    }

    @Test
    public void get() {
        Resume resume = new Resume(UUID_2);
        Assert.assertEquals(resume, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void getAll() {
        Resume[] object = new Resume[storage.size()];
        object[0] = new Resume(UUID_1);
        object[1] = new Resume(UUID_2);
        object[2] = new Resume(UUID_3);
        assertArrayEquals(object, storage.getAll());
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test(expected = StorageException.class)
    public void stackOverFlow() {
        try {
            for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (RuntimeException e) {
            fail("Переполнение произошло раньше времени!");
        }
        storage.save(new Resume());
    }
}