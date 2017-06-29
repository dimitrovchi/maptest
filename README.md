# maptest

Benchmark                                     (selector)  Mode  Cnt   Score   Error   Units
StatBenchmark.aggregate                        ADDER_NEW  avgt    5  63.030 ± 1.658   ns/op
StatBenchmark.aggregate:·gc.alloc.rate         ADDER_NEW  avgt    5   2.559 ± 3.032  MB/sec
StatBenchmark.aggregate:·gc.alloc.rate.norm    ADDER_NEW  avgt    5   0.031 ± 0.038    B/op
StatBenchmark.aggregate:·gc.count              ADDER_NEW  avgt    5     ≈ 0          counts
StatBenchmark.aggregate                      ADDER_REUSE  avgt    5  62.878 ± 1.683   ns/op
StatBenchmark.aggregate:·gc.alloc.rate       ADDER_REUSE  avgt    5   0.019 ± 0.045  MB/sec
StatBenchmark.aggregate:·gc.alloc.rate.norm  ADDER_REUSE  avgt    5  ≈ 10⁻⁴            B/op
StatBenchmark.aggregate:·gc.count            ADDER_REUSE  avgt    5     ≈ 0          counts
