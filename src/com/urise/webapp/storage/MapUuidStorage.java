package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {

   private final Map<String, Resume> mapResume = new LinkedHashMap<>();

    @Override
    protected void toSave(Resume resume, Object index) {
        mapResume.put( (String) index, resume);
    }

    @Override
    protected void toUpdate(Resume resume, Object searchKey) {
        mapResume.put((String) searchKey, resume);
    }

    @Override
    protected Resume toGet(Object searchKey) {
        return  mapResume.get((String) searchKey);
    }

    @Override
    protected void toDelete(Object index) {
        mapResume.remove((String)index);
    }

    @Override
    protected Object searchIndex(String uuid) {
      return uuid;
    }

    @Override
    public void clear() {
        mapResume.clear();
    }

    @Override
    protected List<Resume> toCopyAll() {
        return new ArrayList<>(mapResume.values()) ;
    }

    @Override
    protected boolean isExist(Object index) {
        return mapResume.containsKey(index);
    }

    @Override
    public int size() {
        return mapResume.size();
    }
}