package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private final Map<String, Resume> storageMap = new LinkedHashMap<>();

    @Override
    protected Resume searchIndex(String uuid) {
        return storageMap.get(uuid);
    }

    @Override
    protected void toSave(Resume resume, Resume searchKey) {
        storageMap.put(resume.getUuid(), resume);
    }

    @Override
    protected void toUpdate(Resume resume, Resume searchKey) {
        storageMap.replace(resume.getUuid(), resume);
    }

    @Override
    protected Resume toGet(Resume searchKey) {
        return  searchKey;
    }

    @Override
    protected void toDelete(Resume searchKey) {
        storageMap.remove( searchKey.getUuid());
    }

    @Override
    protected boolean isExist(Resume index) {
        return index != null;
    }

    @Override
    public void clear() {
        storageMap.clear();
    }

    @Override
    protected List<Resume> toCopyAll() {
        return new ArrayList<>(storageMap.values());
    }

    @Override
    public int size() {
        return storageMap.size();
    }
}