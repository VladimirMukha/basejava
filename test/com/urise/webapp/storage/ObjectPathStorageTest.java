package com.urise.webapp.storage;

import com.urise.webapp.storage.serializations.ObjectStream;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorage(storageDir.getName(), new ObjectStream()));
    }
}