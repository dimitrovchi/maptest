package org.dimitrovchi.maptest;

import java.util.Map;
import org.apache.ode.agents.memory.SizingAgent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class MapTestTemplate extends Assert {
    
    protected final Map<String, Integer> map;
    protected final int size = 60;
    
    public MapTestTemplate(Map<String, Integer> map) {
        this.map = map;
    }
    
    @Before
    public void init() throws Exception {
        long startTime = System.nanoTime();
        for (int i = 0; i < size; i++) {
            map.put(i + "x" + i, i);
        }
        double fillTime = (double)(System.nanoTime() - startTime) * 1e-6;
        System.out.println(this + ": fill time = " + fillTime + " ms");
        long ram = SizingAgent.deepSizeOf(map);
        System.out.println(this + ": RAM used = " + ram + " bytes");
    }
    
    @Test
    public void test() throws Exception {
        long startTime = System.nanoTime();
        for (int iter = 0; iter < 100000; iter++) {
            for (int i = 0; i < size; i++) {
                assertNotNull(map.get(i + "x" + i));
            }
        }
        double accessTime = (double)(System.nanoTime() - startTime) * 1e-6;
        System.out.println(this + ": access time = " + accessTime + " ms");
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
