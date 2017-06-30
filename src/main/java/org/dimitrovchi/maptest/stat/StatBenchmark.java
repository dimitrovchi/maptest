package org.dimitrovchi.maptest.stat;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.OptionsBuilder;

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
@OperationsPerInvocation(StatBenchmark.OBJECT_COUNT * 200)
public class StatBenchmark {

    public static final int THREAD_COUNT = 150;
    public static final int OBJECT_COUNT = 500;

    @Benchmark
    public void aggregate(ThreadState state, BenchmarkState benchmarkState) {
        final StatAggregator aggregator = state.aggregator;
        for (final StatObject object : benchmarkState.objects) {
            for (final StatKey key : benchmarkState.keys) {
                aggregator.accept(key, object);
            }
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

        @Setup
        public void setUp(BenchmarkState benchmarkState) {
            aggregator = selector.statAggregatorSupplier.get();
            timer = new ScheduledThreadPoolExecutor(1);
            timer.scheduleAtFixedRate(aggregator::flush, 10000L, 10000L, TimeUnit.MILLISECONDS);
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
