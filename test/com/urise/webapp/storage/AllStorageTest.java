package com.urise.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ArrayStorageTest.class, ListStorageTest.class,
        MapStorageResumeFullNameTest.class,
        SortedArrayStorageTest.class,
        MapUuidStorageTest.class})
public class AllStorageTest {
}
