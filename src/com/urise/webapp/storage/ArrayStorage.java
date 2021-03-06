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
        String uuid = resume.getUuid();
        if (uuid != null) {
            int index = getIndex(uuid);
            if (index == -1) {
                if (size == storage.length) {
                    System.out.println("Место в массиве закончилось! ");
                } else {
                    storage[size] = resume;
                    size++;
                }
            } else {
                checkResume(uuid, index);
            }
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index != -1) {
            storage[index] = resume;
        } else {
            checkResume(resume.getUuid(), index);
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        checkResume(uuid, index);
        return null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            storage[index] = storage[index + 1];
            if (size - index >= 0) System.arraycopy(storage, index +
                    1, storage, index, size - index);
            size--;
        } else {
            checkResume(uuid, index);
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
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

    private void checkResume(String uuid, int index) {
        System.out.println(String.format(index < 0 ? "%s нет в данном %s! "
                + uuid : "%s есть в %s : " + uuid, "Резюме", "списке"));
    }
}