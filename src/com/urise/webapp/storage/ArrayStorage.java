package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        if (uuid != null) {
            int index = getIndex(uuid);
            if (index < 0) {
                if (size == STORAGE_LIMIT) {
                    System.out.println("Место в массиве закончилось! ");
                } else {
                    storage[size] = resume;
                    size++;
                }
            } else {
                showErrorMessage(uuid, index);
            }
        }
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}