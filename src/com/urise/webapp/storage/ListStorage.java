package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

class ListStorage extends AbstractStorage {

    private static final List<Resume> RESUME_LIST = new ArrayList<>();


    @Override
    protected void toSave(Resume resume, Object index) {
        RESUME_LIST.add(resume);
    }

    @Override
    protected Integer searchIndex(String uuid) {
        for (int i = 0; i < RESUME_LIST.size(); i++) {
            if (RESUME_LIST.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Resume toGet(Object searchIndex) {
        return RESUME_LIST.get((Integer) searchIndex);
    }

    @Override
    protected void toDelete(Object index) {
        RESUME_LIST.remove(((Integer) index).intValue());
    }

    @Override
    protected void toUpdate(Resume resume, Object index) {
        RESUME_LIST.set((Integer) index, resume);
    }

    @Override
    protected Resume[] toGetAll() {
        Resume[] resume = new Resume[RESUME_LIST.size()];
        for (int i = 0; i < RESUME_LIST.size(); i++) {
            resume[i] = RESUME_LIST.get(i);
        }
        return resume;
    }

    @Override
    public int size() {
        return RESUME_LIST.size();
    }

    @Override
    public void clear() {
        RESUME_LIST.clear();
    }
}