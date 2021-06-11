package com.urise.webapp.storage;

import com.urise.webapp.storage.serializations.ObjectStreamSerializer;

public class ObjectFileStorageTest extends AbstractStorageTest {

    public ObjectFileStorageTest() {
        super(new FileStorage(storageDir, new ObjectStreamSerializer()));
    }
}