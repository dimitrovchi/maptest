package org.dimitrovchi.maptest.stat;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Dmitry Ovchinnikov
 */
public class StatEventAggregatorAiReuse implements StatAggregator {

    private final EnumMap<StatKey, ConcurrentHashMap<StatObject, AtomicInteger>> stats = new EnumMap<>(StatKey.class);
    private final EnumMap<StatKey, Map<StatObject, LongSet>> results = new EnumMap<>(StatKey.class);

    public StatEventAggregatorAiReuse() {
        EnumSet.allOf(StatKey.class).forEach(k -> stats.put(k, new ConcurrentHashMap<>(512, 0.75f, 150)));
    }

    @Override
    public void accept(StatKey statKey, StatObject statObject) {
        stats.get(statKey).computeIfAbsent(statObject, k -> new AtomicInteger()).incrementAndGet();
    }

    @Override
    public void flush() {
        stats.forEach((k, map) -> map.forEach((o, adder) ->
                results
                        .computeIfAbsent(k, key -> new HashMap<>())
                        .computeIfAbsent(o, so -> new LongOpenHashSet())
                        .add(adder.getAndSet(0))
        ));
    }

    @Override
    public Map<StatKey, Map<StatObject, LongSet>> getResults() {
        return results;
    }
}
