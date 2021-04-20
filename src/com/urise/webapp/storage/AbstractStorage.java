package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected abstract Object searchIndex(String uuid);

    protected abstract void toSave(Resume resume, Object searchKey);

    protected abstract void toUpdate(Resume resume, Object searchKey);

    protected abstract Resume toGet(Object searchKey);

    protected abstract void toDelete(Object searchKey);

    protected abstract boolean isExist(Object index);

    protected abstract List<Resume> toCopyAll();

    @Override
    public void save(Resume resume) {
        toSave(resume, getNotExitSearchIndex(resume.getUuid()));
    }

    @Override
    public void update(Resume resume) {
        toUpdate(resume, getExitSearchIndex(resume.getUuid()));
    }

    @Override
    public Resume get(String uuid) {
        return toGet(getExitSearchIndex(uuid));
    }

    @Override
    public void delete(String uuid) {
        toDelete(getExitSearchIndex(uuid));
    }

    private Object getNotExitSearchIndex(String uuid) {
        Object searchKey = searchIndex(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getExitSearchIndex(String uuid) {
        Object searchKey = searchIndex(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = toCopyAll();
        Collections.sort(list);
        return list;
    }
}