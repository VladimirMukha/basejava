package com.urise.webapp.storage;

import com.urise.webapp.storage.serializations.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest {

    public DataPathStorageTest() {
        super(new PathStorage(storageDir.getName(), new DataStreamSerializer()));
    }
}