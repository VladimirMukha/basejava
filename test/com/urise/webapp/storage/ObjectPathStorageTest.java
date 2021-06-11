package com.urise.webapp.storage;

import com.urise.webapp.storage.serializations.ObjectStreamSerializer;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorage(storageDir.getName(), new ObjectStreamSerializer()));
    }
}