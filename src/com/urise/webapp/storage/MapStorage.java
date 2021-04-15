package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {

    Map<Object, Resume> mapResume = new LinkedHashMap<>();

    @Override
    protected void toSave(Resume resume, Object index) {
        mapResume.put(resume.getUuid(), resume);
    }

    @Override
    protected void toUpdate(Resume resume, Object searchKey) {
        mapResume.replace(searchKey, resume);
    }

    @Override
    protected Resume toGet(Object searchKey) {
        return  (Resume) searchKey;
    }

    @Override
    protected void toDelete(Object index) {
        mapResume.remove(((Resume)index).getUuid());
    }

    @Override
    protected Object searchIndex(String uuid) {
      return mapResume.get(uuid);
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
    protected boolean isExist(Object index) {
        return index != null;
    }

    @Override
    public int size() {
        return mapResume.size();
    }
}