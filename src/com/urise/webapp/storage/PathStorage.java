package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializations.StrategyInterface;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    protected final StrategyInterface pathStrategy;

    public PathStorage(String dir, StrategyInterface objectStream) {
        Objects.requireNonNull(dir, "directory must not be null");
        directory = Paths.get(dir);
        this.pathStrategy = objectStream;
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
            throw new StorageException("create file error", resume.getUuid(), e);
        }
        toUpdate(resume, path);
    }

    @Override
    protected void toUpdate(Resume resume, Path path) {
        try {
            pathStrategy.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("update error", resume.getUuid(), e);
        }
    }

    @Override
    protected Resume toGet(Path path) {
        try {
            return pathStrategy.doRead(new BufferedInputStream(Files.newInputStream(path)));
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
        return getStream().map(this::toGet).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getStream().forEach(this::toDelete);
    }

    @Override
    public int size() {
        return (int) getStream().count();
    }

    public Stream<Path> getStream() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("not read directory", null, e);
        }
    }
}