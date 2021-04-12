package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    protected abstract void insert(Resume resume, int index);

    protected abstract void remove(Integer index);

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void toSave(Resume resume, Object index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insert(resume, (Integer) index);
        size++;
    }

    public void toUpdate(Resume resume, Object index) {
        storage[(Integer) index] = resume;
    }

    public void toDelete(Object index) {
        remove((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    public Resume toGet(Object uuid) {
        return storage[(Integer) uuid];
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}