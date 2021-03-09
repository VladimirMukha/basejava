package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage implements Storage {
    private static final int STORAGE_LIMIT = 10_000;
    private Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        String uuid = resume.getUuid();
        if (uuid != null) {
            int index = getIndex(uuid);
            if (index == -1) {
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

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index != -1) {
            storage[index] = resume;
        } else {
            showErrorMessage(resume.getUuid(), index);
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        showErrorMessage(uuid, index);
        return null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            if (size - index >= 0) System.arraycopy(storage, index + 1, storage, index, size - index);
            size--;
        } else {
            showErrorMessage(uuid, index);
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}