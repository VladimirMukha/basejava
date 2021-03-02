package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int size;
    private Resume[] storage = new Resume[10_000];
    private boolean isResume = false;
    private int index;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (resume.getUuid() != null) {
            int i = 0;
            while (i < size()) {
                if (resume.getUuid().equals(storage[i].getUuid()) || size() > storage.length) {
                    index = i;
                    isFindResume(isResume);
                    break;
                }
                i++;
            }
            if (size == 0 || storage[i] == null) {
                storage[size] = resume;
                size++;
            }
        }
    }

    public void update(Resume resume) {
        Resume newResume = new Resume();
        newResume.setUuid("new Resume");
        for (int i = 0; i < size(); i++) {
            if (resume.getUuid().equals(storage[i].getUuid())) {
                isResume = true;
                storage[i] = newResume;
                break;
            }
        }
        if (!isResume) {
            isFindResume(true);
        }
    }

    public Resume get(String uuid) {
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
                if (size() - i >= 0) System.arraycopy(storage, i + 1, storage, i, size() - i);
                size--;
            }
        }
        if (!isResume) {
            isFindResume(true);
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private void isFindResume(boolean resume) {
        System.out.println(String.format(resume ? "%s нет %s  " : "%s уже есть %s! : " +
                storage[index].getUuid(), "ERROR: Резюме", " в списке"));
    }
}