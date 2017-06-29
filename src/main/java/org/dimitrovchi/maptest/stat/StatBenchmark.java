package org.dimitrovchi.maptest.stat;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.stream.IntStream.range;

/**
 * @author Dmitry Ovchinnikov
 */
@Measurement(iterations = 5)
@Warmup(iterations = 5)
@Fork(value = 1, jvmArgs = {"-XX:+UseG1GC", "-Xms10g", "-Xmx10g"})
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Threads(StatBenchmark.THREAD_COUNT)
@BenchmarkMode(Mode.AverageTime)
@OperationsPerInvocation(StatBenchmark.COUNT)
public class StatBenchmark {

    public static final int THREAD_COUNT = 8;
    public static final int COUNT = 1_000_000;
    public static final int OBJECT_COUNT = 500;

    @Benchmark
    public void aggregate(ThreadState state) {
        final StatKey[] keys = state.keys;
        final StatObject[] objects = state.objects;
        final StatAggregator aggregator = state.aggregator;
        for (int i = 0; i < COUNT; i++) {
            aggregator.accept(keys[i], objects[i]);
        }
    }

    @State(Scope.Benchmark)
    public static class BenchmarkState {

        private final StatKey[] keys = StatKey.values();
        private final StatObject[] objects = range(0, OBJECT_COUNT).mapToObj(StatObject::new).toArray(StatObject[]::new);
    }

    @State(Scope.Thread)
    public static class ThreadState {

        @Param
        private StatAggregatorSelector selector;

        private StatAggregator aggregator;
        private ScheduledThreadPoolExecutor timer;
        private StatKey[] keys;
        private StatObject[] objects;

        @Setup
        public void setUp(BenchmarkState benchmarkState) {
            keys = new Random(0L).ints(COUNT, 0, benchmarkState.keys.length)
                    .mapToObj(i -> benchmarkState.keys[i])
                    .toArray(StatKey[]::new);
            objects = new Random(0L).ints(COUNT, 0, benchmarkState.objects.length)
                    .mapToObj(i -> benchmarkState.objects[i])
                    .toArray(StatObject[]::new);
            aggregator = selector.statAggregatorSupplier.get();
            timer = new ScheduledThreadPoolExecutor(1);
            timer.scheduleAtFixedRate(aggregator::flush, 1000L, 1000L, TimeUnit.MILLISECONDS);
        }

        @TearDown
        public void tearDown() throws InterruptedException, TimeoutException {
            timer.shutdown();
            if (!timer.awaitTermination(1L, TimeUnit.SECONDS)) {
                throw new TimeoutException();
            }
        }
    }

    public static void main(String... args) throws Exception {
        new Runner(new OptionsBuilder()
                .addProfiler(GCProfiler.class)
                .build()).run();
    }
}
