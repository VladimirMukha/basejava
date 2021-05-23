package com.urise.webapp.storage;

public class ObjectFileStorageTest extends AbstractStorageTest {

    public ObjectFileStorageTest(){
        super(new FileStorage(storageDir));
    }
}