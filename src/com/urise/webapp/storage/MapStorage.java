package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {

    Map<Object, Resume> mapResume = new LinkedHashMap<>();

    @Override
    protected void toSave(Resume resume, Object index) {
        mapResume.put(new Resume().getUuid(), resume);
    }

    @Override
    protected void toUpdate(Resume resume, Object searchKey) {
        mapResume.put(searchKey, resume);
    }

    @Override
    protected Resume toGet(Object searchKey) {
        return mapResume.get(searchKey);
    }

    @Override
    protected void toDelete(Object index) {
        mapResume.remove(index);
    }

    @Override
    protected Integer searchIndex(String uuid) {
        for (int i = 0; i < mapResume.size(); i++) {
            if (mapResume.containsKey(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void clear() {
        mapResume.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[mapResume.size()];
        return mapResume.values().toArray(resumes);
    }

    @Override
    public int size() {
        return mapResume.size();
    }
}