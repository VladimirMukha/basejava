package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    private static Logger logger = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract SK searchIndex(String uuid);

    protected abstract void toSave(Resume resume, SK searchKey);

    protected abstract void toUpdate(Resume resume, SK searchKey);

    protected abstract Resume toGet(SK searchKey);

    protected abstract void toDelete(SK searchKey);

    protected abstract boolean isExist(SK index);

    protected abstract List<Resume> toCopyAll();

    @Override
    public void save(Resume resume) {
        logger.info("Save" + resume);
        toSave(resume, getNotExitSearchIndex(resume.getUuid()));
    }

    @Override
    public void update(Resume resume) {
        logger.info("Delete" + resume);
        toUpdate(resume, getExitSearchIndex(resume.getUuid()));
    }

    @Override
    public Resume get(String uuid) {
        logger.warning("Get" + uuid);
        return toGet(getExitSearchIndex(uuid));
    }

    @Override
    public void delete(String uuid) {
        logger.info("Delete" + uuid);
        toDelete(getExitSearchIndex(uuid));
    }

    private SK getNotExitSearchIndex(String uuid) {
        SK searchKey = searchIndex(uuid);
        if (isExist(searchKey)) {
            logger.info("Resume " + uuid + " already exist ");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getExitSearchIndex(String uuid) {
        SK searchKey = searchIndex(uuid);
        if (!isExist(searchKey)) {
            logger.info("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public List<Resume> getAllSorted() {
        logger.info("GetSortList");
        List<Resume> list = toCopyAll();
        list.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return list;
    }
}