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
    private Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

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
        storage.save(new Resume());
        Assert.assertEquals(4, storage.size());
    }

    @Test
    public void update() {
        storage.update(new Resume(UUID_1));
        Assert.assertEquals(storage.get(UUID_1), storage.getAll()[0]);
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.getAll().length);
    }

    @Test
    public void get() {
        Resume resume = storage.get(UUID_1);
        Assert.assertEquals(resume, storage.getAll()[0]);
    }

    @Test(expected = ExistStorageException.class)
    public void getExist() {
        storage.save(new Resume(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void getAll() {
        Resume[] object = new Resume[storage.size()];
        for (int i = 0; i < storage.size(); i++) {
            object[i] = storage.getAll()[i];
        }
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