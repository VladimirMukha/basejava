package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int size;
    private Resume[] storage = new Resume[10000];
    private boolean isResume = false;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (r.getUuid() != null) {
            int i = 0;
            while (i < size()) {
                if (r.getUuid().equals(storage[i].getUuid()) || size() > storage.length) {
                    System.out.println("ERROR: Резюме уже есть в списке!");
                    break;
                }
                i++;
            }
            if (size == 0) {
                storage[size] = r;
                size++;
            }
            if (storage[i] == null) {
                storage[size] = r;
                size++;
            }
        }
    }

    public void update(Resume r) {
        isResume = false;
        Resume resume = new Resume();
        resume.setUuid("new Resume");
        for (int i = 0; i < size(); i++) {
            if (r.getUuid().equals(storage[i].getUuid())) {
                isResume = true;
                storage[i] = resume;
                break;
            }
        }
        if (!isResume) {
            System.out.println("ERROR: Резюме нет в списке!");
        }
    }

    public Resume get(String uuid) {
        isResume = false;
        for (int i = 0; i < size(); i++) {
            if (storage[i].getUuid().equals(uuid)) {
                isResume = true;
                return storage[i];
            }
        }
        isFindResume(isResume);
        return null;
    }

    public void delete(String uuid) {
        isResume = false;
        for (int i = 0; i < size(); i++) {
            if (storage[i].getUuid().equals(uuid)) {
                isResume = true;
                storage[i] = storage[i + 1];
                for (int j = i; j < size(); j++) {
                    storage[j] = storage[j + 1];
                }
                size--;
            }
        }
        isFindResume(isResume);
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private void isFindResume(boolean resume) {
        if (!resume) {
            System.out.println("ERROR: Резюме нет в списке!");
        }
    }
}