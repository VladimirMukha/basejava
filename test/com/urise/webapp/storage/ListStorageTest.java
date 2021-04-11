package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ListStorageTest {
    private static final Storage RESUME_LIST = new ListStorage();

    private static final String RESUME_1 = "UUID1";
    private static final String RESUME_2 = "UUID2";
    private static final String RESUME_3 = "UUID3";
    private static final String RESUME_4 = "UUID4";

    @Before
    public void setUp() {
        RESUME_LIST.clear();
        RESUME_LIST.save(new Resume(RESUME_1));
        RESUME_LIST.save(new Resume(RESUME_2));
        RESUME_LIST.save(new Resume(RESUME_3));
    }

    @Test
    public void toSave() {
        Resume resume = new Resume(RESUME_4);
        RESUME_LIST.save(resume);
        Assert.assertEquals(resume, RESUME_LIST.get(RESUME_4));
        Assert.assertEquals(4, RESUME_LIST.size());
    }

    @Test
    public void toGet() {
        List<Resume> list = new ArrayList<>();
        list.add(new Resume(RESUME_1));
        list.add(new Resume(RESUME_2));
        list.add(new Resume(RESUME_3));
        Assert.assertEquals(list.get(0), RESUME_LIST.get(RESUME_1));
        Assert.assertEquals(list.get(1), RESUME_LIST.get(RESUME_2));
        Assert.assertEquals(list.get(2), RESUME_LIST.get(RESUME_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void toDelete() {
        RESUME_LIST.delete(RESUME_1);
        try {
            RESUME_LIST.get(RESUME_1);
        } catch (NotExistStorageException e) {
            throw new NotExistStorageException(RESUME_1);
        }
        Assert.assertEquals(2, RESUME_LIST.size());
    }

    @Test
    public void toUpdate() {
        Resume resume = new Resume(RESUME_1);
        RESUME_LIST.update(resume);
        Assert.assertEquals(resume, RESUME_LIST.get(RESUME_1));
    }

    @Test
    public void toGetAll() {
        Resume[] resume = {new Resume(RESUME_1), new Resume(RESUME_2), new Resume(RESUME_3)};
        Assert.assertArrayEquals(resume, RESUME_LIST.getAll());
    }

    @Test
    public void size() {
        assertEquals(3, RESUME_LIST.size());
    }

    @Test
    public void clear() {
        RESUME_LIST.clear();
        Assert.assertEquals(0, RESUME_LIST.size());
    }
}