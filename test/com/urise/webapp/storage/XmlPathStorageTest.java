package com.urise.webapp.storage;

import com.urise.webapp.storage.serializations.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest {

    public XmlPathStorageTest() {
        super(new PathStorage(storageDir.getName(),new XmlStreamSerializer()));
    }
}