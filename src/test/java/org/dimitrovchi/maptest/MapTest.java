package org.dimitrovchi.maptest;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import it.unimi.dsi.fastutil.objects.Object2IntAVLTreeMap;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntRBTreeMap;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import org.apache.wicket.core.util.lang.WicketObjects;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(value = Parameterized.class)
@BenchmarkOptions(concurrency = BenchmarkOptions.CONCURRENCY_SEQUENTIAL)
public class MapTest<T extends Map<String, Integer> & Serializable> extends AbstractBenchmark {
    
    protected final T map;
    protected final int size;
    protected final Runtime runtime = Runtime.getRuntime();
    
    @SuppressWarnings("LeakingThisInConstructor")
    public MapTest(T map, int size) {
        this.size = size;
        this.map = map;
        for (int i = 0; i < size; i++) {
            map.put(i + "x" + i, i);
        }
        System.out.println();
        System.out.println(this);
    }
    
    @Test
    @BenchmarkOptions(benchmarkRounds = 1000)
    public void testFill() throws Exception {
        Map m = map.getClass().newInstance();
        for (int i = 0; i < size; i++) {
            m.put(i + "x" + i, i);
        }
    }
    
    @Test
    @BenchmarkOptions(benchmarkRounds = 100000)
    public void testAccess() throws Exception {
        for (int i = 0; i < size; i++) {
            assertNotNull(map.get(i + "x" + i));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(map.getClass().getSimpleName());
        sb.append('(');
        sb.append(size);
        sb.append(" elements, ");
        sb.append(WicketObjects.sizeof(map));
        sb.append(" bytes)");
        return sb.toString();
    }
    
    @Parameterized.Parameters
    public static Collection<Object[]> params() {
        List<Object[]> ps = new ArrayList<Object[]>();
        int[] sizes = {10, 50, 100, 300, 500};
        for (int i = 0; i < sizes.length; i++) {
            ps.add(new Object[] {new HashMap(), sizes[i]});
            ps.add(new Object[] {new TreeMap(), sizes[i]});
            ps.add(new Object[] {new LinkedHashMap(), sizes[i]});
            ps.add(new Object[] {new ConcurrentHashMap(), sizes[i]});
            ps.add(new Object[] {new ConcurrentSkipListMap(), sizes[i]});
            ps.add(new Object[] {new Object2IntAVLTreeMap(), sizes[i]});
            ps.add(new Object[] {new Object2IntLinkedOpenHashMap(), sizes[i]});
            ps.add(new Object[] {new Object2IntOpenHashMap(), sizes[i]});
            ps.add(new Object[] {new Object2IntRBTreeMap(), sizes[i]});
        }
        return ps;
    }
}
