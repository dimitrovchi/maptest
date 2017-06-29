package org.dimitrovchi.maptest.stat;

import it.unimi.dsi.fastutil.longs.LongSet;

import java.io.Flushable;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author Dmitry Ovchinnikov
 */
public interface StatAggregator extends BiConsumer<StatKey, StatObject>, Flushable {

    @Override
    void flush();

    Map<StatKey, Map<StatObject, LongSet>> getResults();
}
