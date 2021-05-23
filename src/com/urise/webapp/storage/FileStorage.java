package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializations.ObjectStreamStorage;
import com.urise.webapp.storage.serializations.SerializableStorage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    protected SerializableStorage streamStorage = new ObjectStreamStorage();

    protected FileStorage(File directory) {
        Objects.requireNonNull(directory, "directory most not by null");
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
            throw new StorageException("File write error", resume.getUuid(), e);
        }
        toUpdate(resume, file);
    }

    @Override
    protected void toUpdate(Resume resume, File os) {
        try {
            streamStorage.doWrite(resume, new BufferedOutputStream(new FileOutputStream(os)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Resume toGet(File file) {
        try {
            return streamStorage.doRead(new BufferedInputStream(new FileInputStream(file)));
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
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("File read error", null);
        }
        List<Resume> list = new ArrayList<>(files.length);
        for (File file : files) {
            list.add(toGet(file));
        }
        return list;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                toDelete(file);
            }
        }
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list == null) {
            throw new StorageException("Directory read error", null);
        }
        return list.length;
    }
}