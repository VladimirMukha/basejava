package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    protected abstract void insert(Resume resume, int index);

    protected abstract void remove(Integer index);

    protected abstract Integer searchIndex(String uuid);

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void toSave(Resume resume, Integer index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insert(resume, index);
        size++;
    }

    public void toUpdate(Resume resume, Integer index) {
        storage[index] = resume;
    }

    public void toDelete(Integer index) {
        remove(index);
        storage[size - 1] = null;
        size--;
    }

    public Resume toGet(Integer uuid) {
        return storage[uuid];
    }

    @Override
    protected List<Resume> toCopyAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Integer index) {
        return (index >= 0);
    }
}