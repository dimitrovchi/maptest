# maptest

```
Benchmark                                     (selector)  Mode  Cnt   Score   Error   Units
StatBenchmark.aggregate                        ADDER_NEW  avgt    5  61.770 ± 1.545   ns/op
StatBenchmark.aggregate:·gc.alloc.rate         ADDER_NEW  avgt    5   2.571 ± 3.373  MB/sec
StatBenchmark.aggregate:·gc.alloc.rate.norm    ADDER_NEW  avgt    5   0.031 ± 0.041    B/op
StatBenchmark.aggregate:·gc.count              ADDER_NEW  avgt    5     ≈ 0          counts
StatBenchmark.aggregate                      ADDER_REUSE  avgt    5  63.159 ± 2.292   ns/op
StatBenchmark.aggregate:·gc.alloc.rate       ADDER_REUSE  avgt    5   0.028 ± 0.069  MB/sec
StatBenchmark.aggregate:·gc.alloc.rate.norm  ADDER_REUSE  avgt    5  ≈ 10⁻³            B/op
StatBenchmark.aggregate:·gc.count            ADDER_REUSE  avgt    5     ≈ 0          counts
StatBenchmark.aggregate                           AI_NEW  avgt    5  60.038 ± 1.987   ns/op
StatBenchmark.aggregate:·gc.alloc.rate            AI_NEW  avgt    5   0.022 ± 0.047  MB/sec
StatBenchmark.aggregate:·gc.alloc.rate.norm       AI_NEW  avgt    5  ≈ 10⁻⁴            B/op
StatBenchmark.aggregate:·gc.count                 AI_NEW  avgt    5     ≈ 0          counts
```
