package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract void toSave(Resume resume, Object index);

    protected abstract void toUpdate(Resume resume, Object searchKey);

    protected abstract Resume toGet(Object searchKey);

    protected abstract void toDelete(Object index);

    protected abstract Object searchIndex(String uuid);

    protected abstract boolean isExist(Object index);

    @Override
    public void save(Resume resume) {
        if (resume != null) {
            toSave(resume, getExistSearchIndex(resume.getUuid()));
        }
    }

    @Override
    public void update(Resume resume) {
        toUpdate(resume, getNotExitSearchIndex(resume.getUuid()));
    }

    @Override
    public Resume get(String uuid) {
        return toGet(getNotExitSearchIndex(uuid));
    }

    @Override
    public void delete(String uuid) {
        toDelete(getNotExitSearchIndex(uuid));
    }

    private Object getExistSearchIndex(String uuid) {
        Object index = searchIndex(uuid);
        if (isExist(index)) {

            throw new ExistStorageException(uuid);
        }
        return index;
    }

    private Object getNotExitSearchIndex(String uuid) {
        Object index = searchIndex(uuid);
        if (!isExist(index)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }
}