package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> resumeArrayList = new ArrayList<>();

    @Override
    protected void toSave(Resume resume, Object index) {
        resumeArrayList.add(resume);
    }

    @Override
    protected Integer searchIndex(String uuid) {
        for (int i = 0; i < resumeArrayList.size(); i++) {
            if (resumeArrayList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected Resume toGet(Object searchIndex) {
        return resumeArrayList.get((Integer) searchIndex);
    }

    @Override
    protected void toDelete(Object index) {
        resumeArrayList.remove(((Integer) index).intValue());
    }

    @Override
    protected void toUpdate(Resume resume, Object index) {
        resumeArrayList.set((Integer) index, resume);
    }

    @Override
    public Resume[] getAll() {
        Resume[] resume = new Resume[resumeArrayList.size()];
        for (int i = 0; i < resumeArrayList.size(); i++) {
            resume[i] = resumeArrayList.get(i);
        }
        return resume;
    }

    @Override
    protected boolean isExist(Object index) {
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