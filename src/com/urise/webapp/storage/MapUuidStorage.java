package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {

    private final Map<String, Resume> mapResume = new LinkedHashMap<>();

    @Override
    protected void toSave(Resume resume, String index) {
        mapResume.put(index, resume);
    }

    @Override
    protected void toUpdate(Resume resume, String searchKey) {
        mapResume.put(searchKey, resume);
    }

    @Override
    protected Resume toGet(String searchKey) {
        return mapResume.get(searchKey);
    }

    @Override
    protected void toDelete(String index) {
        mapResume.remove(index);
    }

    @Override
    protected String searchIndex(String uuid) {
        return uuid;
    }

    @Override
    public void clear() {
        mapResume.clear();
    }

    @Override
    protected List<Resume> toCopyAll() {
        return new ArrayList<>(mapResume.values());
    }

    @Override
    protected boolean isExist(String index) {
        return mapResume.containsKey(index);
    }

    @Override
    public int size() {
        return mapResume.size();
    }
}