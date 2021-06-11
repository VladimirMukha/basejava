package com.urise.webapp.storage;

import com.urise.webapp.storage.serializations.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super( new PathStorage(storageDir.getName(),new JsonStreamSerializer()));
    }
}