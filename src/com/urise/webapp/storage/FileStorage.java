package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializations.StrategyInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    protected StrategyInterface objectStream;


    protected FileStorage(File directory, StrategyInterface strategyInterface) {
        Objects.requireNonNull(directory, "directory most not by null");
        this.objectStream = strategyInterface;
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "not is directory!");
        }
        if (!directory.canWrite() || !directory.canRead()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected File searchIndex(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void toSave(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("File write error", file.getName(), e);
        }
        toUpdate(resume, file);
    }

    @Override
    protected void toUpdate(Resume resume, File os) {
        try {
            objectStream.doWrite(resume, new BufferedOutputStream(new FileOutputStream(os)));
        } catch (IOException e) {
            throw new StorageException("File write error", os.getName(), e);
        }
    }

    @Override
    protected Resume toGet(File file) {
        try {
            return objectStream.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void toDelete(File file) {
        if (!file.delete()) {
            throw new StorageException(file.getName(), "File delete error ! ");
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> toCopyAll() {
        List<Resume> list = new ArrayList<>();
        for (File file : getListFiles()) {
            list.add(toGet(file));
        }
        return list;
    }

    @Override
    public void clear() {
        for (File file : getListFiles()) {
            toDelete(file);
        }
    }

    @Override
    public int size() {
        return getListFiles().length;
    }

    public File[] getListFiles() {
        File[] list = directory.listFiles();
        if (list == null) {
            throw new StorageException("Directory read error", null);
        }
        return list;
    }
}