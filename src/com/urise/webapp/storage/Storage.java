package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public interface Storage {

    void clear();

    void save(Resume resume);

    void update(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    Resume[] getAll();

    int size();

    default void showErrorMessage(String uuid, int index) {
        System.out.println(String.format(index < 0 ? "%s нет в данном %s! "
                + uuid : "%s есть в %s : " + uuid, "Резюме", "списке"));
    }
}