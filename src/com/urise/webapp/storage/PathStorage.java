package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializations.ObjectStreamStorage;
import com.urise.webapp.storage.serializations.SerializableStorage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    protected final SerializableStorage objectStream = new ObjectStreamStorage();

    public PathStorage(String dir) {
        Objects.requireNonNull(dir, "directory must not be null");
        directory = Paths.get(dir);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + "is not directory or not  writable");
        }
    }

    @Override
    protected Path searchIndex(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void toSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        toUpdate(resume, path);
    }

    @Override
    protected void toUpdate(Resume resume, Path path) {
        try {
            objectStream.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("update error", resume.getUuid(), e);
        }
    }

    @Override
    protected Resume toGet(Path path) {
        try {
            return objectStream.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("error read file", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void toDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("error delete file", path.getFileName().toString(), e);
        }
    }

    @Override
    protected boolean isExist(Path index) {
        return Files.isRegularFile(index);
    }

    @Override
    protected List<Resume> toCopyAll() {
        List<Resume> resultList = new ArrayList<>();
        try {
            List<Path> list = Files.list(directory).collect(Collectors.toList());
            for (Path d : list) {
                resultList.add(toGet(d));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::toDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", e.getMessage());
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("error size", null, e);
        }
    }
}