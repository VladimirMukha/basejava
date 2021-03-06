package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    protected void insert(Resume resume, int index) {
        storage[size] = resume;
    }

    protected Integer searchIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void remove(Integer index) {
        storage[index] = storage[size - 1];
    }
}