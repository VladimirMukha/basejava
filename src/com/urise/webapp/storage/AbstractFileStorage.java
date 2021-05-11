package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory most not by null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "not is directory!");
        }
        if (!directory.canWrite() || !directory.canRead()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is readable/writable");
        }
        this.directory = directory;
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    protected abstract Resume doRead(File file);

    @Override
    protected File searchIndex(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void toSave(Resume resume, File file) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }


    @Override
    protected void toUpdate(Resume resume, File file) {
        try {
            if (file.exists()) {
                doWrite(resume, file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected Resume toGet(File file) {
        return doRead(file);
    }


    @Override
    protected void toDelete(File file) {
        for (File f : directory.listFiles()) {
            if (f.equals(file)) {
                f.delete();
            }
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> toCopyAll() {
        return Arrays.asList(doRead(directory));
    }

    @Override
    public void clear() {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.delete()) {
                System.out.println("Files is clear");
            }
        }
    }

    @Override
    public int size() {
        var count = 0;
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isFile()) {
                count++;
            }
        }
        return count;
    }
}