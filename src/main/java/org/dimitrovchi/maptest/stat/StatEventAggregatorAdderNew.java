package org.dimitrovchi.maptest.stat;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Dmitry Ovchinnikov
 */
public class StatEventAggregatorAdderNew implements StatAggregator {

    private final EnumMap<StatKey, ConcurrentHashMap<StatObject, LongAdder>> stats = new EnumMap<>(StatKey.class);
    private final EnumMap<StatKey, Map<StatObject, LongSet>> results = new EnumMap<>(StatKey.class);

    public StatEventAggregatorAdderNew() {
        EnumSet.allOf(StatKey.class).forEach(k -> stats.put(k, new ConcurrentHashMap<>(512, 0.75f, 150)));
    }

    @Override
    public void accept(StatKey statKey, StatObject statObject) {
        stats.get(statKey).computeIfAbsent(statObject, k -> new LongAdder()).increment();
    }

    @Override
    public void flush() {
        stats.forEach((k, map) -> map.entrySet().removeIf(e -> {
            results
                    .computeIfAbsent(k, key -> new HashMap<>())
                    .computeIfAbsent(e.getKey(), key -> new LongOpenHashSet())
                    .add(e.getValue().sum());
            return true;
        }));
    }

    @Override
    public Map<StatKey, Map<StatObject, LongSet>> getResults() {
        return results;
    }
}
