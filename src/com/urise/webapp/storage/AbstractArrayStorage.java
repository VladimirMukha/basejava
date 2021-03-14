package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        String uuid = resume.getUuid();
        int index = getIndex(uuid);
        if (size == STORAGE_LIMIT) {
            System.out.println("Место в массиве закончилось! ");
            return;
        }
        if (uuid != null) {
            if (index < 0) {
                insert(resume, index);
                size++;
            } else
                showErrorMessage(uuid, index);
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index != -1) {
            storage[index] = resume;
        } else {
            showErrorMessage(resume.getUuid(), index);
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            remove(index);
            size--;
        } else {
            showErrorMessage(uuid, index);
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        showErrorMessage(uuid, index);
        return null;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected abstract void insert(Resume resume, int index);

    protected abstract int getIndex(String uuid);

    protected abstract void remove(int index);
}