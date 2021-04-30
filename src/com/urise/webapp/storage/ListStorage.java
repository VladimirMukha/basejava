package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private final List<Resume> resumeArrayList = new ArrayList<>();

    @Override
    protected void toSave(Resume resume, Integer index) {
        resumeArrayList.add(resume);
    }

    @Override
    protected Integer searchIndex(String uuid) {
        for (var i = 0; i < resumeArrayList.size(); i++) {
            if (resumeArrayList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected Resume toGet(Integer searchIndex) {
        return resumeArrayList.get(searchIndex);
    }

    @Override
    protected void toDelete(Integer index) {
        resumeArrayList.remove((index).intValue());
    }

    @Override
    protected void toUpdate(Resume resume, Integer index) {
        resumeArrayList.set(index, resume);
    }

    @Override
    protected List<Resume> toCopyAll() {
        return resumeArrayList;
    }

    @Override
    protected boolean isExist(Integer index) {
        return index != null;
    }

    @Override
    public int size() {
        return resumeArrayList.size();
    }

    @Override
    public void clear() {
        resumeArrayList.clear();
    }
}