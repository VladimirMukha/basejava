package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage {
    private int size;
    private Resume[] storage = new Resume[10_000];

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (resume.getUuid() != null) {
            int result = findResume(resume.getUuid());
            if (result < 0) {
                if (size == 0 || storage[size] == null && storage.length > size()) {
                    storage[size] = resume;
                    size++;
                }
            } else
                showInfo(resume.getUuid(), result);
        }
    }

    public void update(Resume resume) {
        Resume newResume = new Resume();
        newResume.setUuid("new Resume");
        int result = findResume(resume.getUuid());
        if (result >= 0) {
            storage[result] = newResume;
        } else
            showInfo(resume.getUuid(), result);
    }

    public Resume get(String uuid) {
        int result = findResume(uuid);
        if (result >= 0) {
            return storage[result];
        } else
            showInfo(uuid, result);
        return null;
    }

    public void delete(String uuid) {
        int result = findResume(uuid);
        if (result >= 0) {
            storage[result] = storage[result + 1];
            if (size() - result >= 0) System.arraycopy(storage, result +
                    1, storage, result, size() - result);
            size--;
        } else
            showInfo(uuid, result);
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int findResume(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    private void showInfo(String uuid, int index) {
        System.out.println(String.format(index < 0 ? "%s нет в данном %s! "
                + uuid : "%s есть в %s :" + uuid, "Резюме", "списке"));
    }
}