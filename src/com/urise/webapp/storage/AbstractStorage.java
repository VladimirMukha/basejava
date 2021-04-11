package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract void toSave(Resume resume, Object index);

    protected abstract void toUpdate(Resume resume, Object searchKey);

    protected abstract Resume toGet(Object searchKey);

    protected abstract Resume[] toGetAll();

    protected abstract void toDelete(Object index);

    protected abstract Integer searchIndex(String uuid);


    @Override
    public void save(Resume resume) {
        toSave(resume, existSearchIndex(resume.getUuid()));
    }

    @Override
    public void update(Resume resume) {
        toUpdate(resume, notExitSearchIndex(resume.getUuid()));
    }

    @Override
    public Resume get(String uuid) {
        return toGet(notExitSearchIndex(uuid));
    }

    @Override
    public Resume[] getAll() {
        return toGetAll();
    }

    @Override
    public void delete(String uuid) {
        toDelete(notExitSearchIndex(uuid));
    }

    private int existSearchIndex(String uuid) {
        int index = searchIndex(uuid);
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        } else
            return index;
    }

    private int notExitSearchIndex(String uuid) {
        int index = searchIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else
            return index;
    }
}