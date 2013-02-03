package org.dimitrovchi.maptest;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest extends MapTestTemplate {
    public ConcurrentHashMapTest() {
        super(new ConcurrentHashMap<String, Integer>());
    }
}
