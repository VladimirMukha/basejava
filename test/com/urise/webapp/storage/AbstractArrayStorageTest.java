package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected File storageDir = new File("d:/fileNew");

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void stackOverFlow() {
        try {
            for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("new Resume"));
            }
        } catch (StorageException e) {
            fail("Переполнение произошло раньше времени!");
        }
        storage.save(new Resume("new Resume"));
    }
}