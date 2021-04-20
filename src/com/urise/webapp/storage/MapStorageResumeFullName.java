package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorageResumeFullName extends AbstractStorage {
    private final Map<String, Resume> storageMap = new LinkedHashMap<>();

    @Override
    protected Object searchIndex(String uuid) {
        return storageMap.get(uuid);
    }

    @Override
    protected void toSave(Resume resume, Object searchKey) {
        storageMap.put(resume.getUuid(), resume);
    }

    @Override
    protected void toUpdate(Resume resume, Object searchKey) {
        storageMap.replace(resume.getUuid(), resume);
    }

    @Override
    protected Resume toGet(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void toDelete(Object searchKey) {
        storageMap.remove(((Resume) searchKey).getUuid());
    }

    @Override
    protected boolean isExist(Object index) {
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