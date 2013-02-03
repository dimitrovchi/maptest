package org.dimitrovchi.maptest;

import java.util.concurrent.ConcurrentSkipListMap;

public class SkipListMapTest extends MapTestTemplate {
    public SkipListMapTest() {
        super(new ConcurrentSkipListMap<String, Integer>());
    }
}
