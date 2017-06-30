# maptest

```
Threads: 150
Renew period: 10s
Stat objects: 500
Stat keys: 200
JVM flags: -XX:+UseG1GC -Xmx12g -Xms12g
```

```
Benchmark                                              (selector)  Mode  Cnt     Score      Error   Units
StatBenchmark.aggregate                                 ADDER_NEW  avgt   10  2860.641 ± 1577.193   ns/op
StatBenchmark.aggregate:·gc.alloc.rate                  ADDER_NEW  avgt   10   131.104 ±  311.897  MB/sec
StatBenchmark.aggregate:·gc.alloc.rate.norm             ADDER_NEW  avgt   10     4.232 ±   10.212    B/op
StatBenchmark.aggregate:·gc.churn.G1_Eden_Space         ADDER_NEW  avgt   10   130.477 ±  322.093  MB/sec
StatBenchmark.aggregate:·gc.churn.G1_Eden_Space.norm    ADDER_NEW  avgt   10     4.290 ±   10.797    B/op
StatBenchmark.aggregate:·gc.count                       ADDER_NEW  avgt   10     9.000             counts
StatBenchmark.aggregate:·gc.time                        ADDER_NEW  avgt   10  2465.000                 ms
StatBenchmark.aggregate                               ADDER_REUSE  avgt   10  2775.941 ±  894.291   ns/op
StatBenchmark.aggregate:·gc.alloc.rate                ADDER_REUSE  avgt   10     0.139 ±    0.307  MB/sec
StatBenchmark.aggregate:·gc.alloc.rate.norm           ADDER_REUSE  avgt   10     0.004 ±    0.008    B/op
StatBenchmark.aggregate:·gc.count                     ADDER_REUSE  avgt   10       ≈ 0             counts
StatBenchmark.aggregate                                    AI_NEW  avgt   10  2772.938 ± 2217.715   ns/op
StatBenchmark.aggregate:·gc.alloc.rate                     AI_NEW  avgt   10   111.579 ±  240.664  MB/sec
StatBenchmark.aggregate:·gc.alloc.rate.norm                AI_NEW  avgt   10     2.874 ±    6.463    B/op
StatBenchmark.aggregate:·gc.churn.G1_Eden_Space            AI_NEW  avgt   10    99.654 ±  242.596  MB/sec
StatBenchmark.aggregate:·gc.churn.G1_Eden_Space.norm       AI_NEW  avgt   10     2.659 ±    6.502    B/op
StatBenchmark.aggregate:·gc.count                          AI_NEW  avgt   10     9.000             counts
StatBenchmark.aggregate:·gc.time                           AI_NEW  avgt   10  2453.000                 ms
StatBenchmark.aggregate                                  AI_REUSE  avgt   10  2494.508 ± 1176.741   ns/op
StatBenchmark.aggregate:·gc.alloc.rate                   AI_REUSE  avgt   10     0.198 ±    0.423  MB/sec
StatBenchmark.aggregate:·gc.alloc.rate.norm              AI_REUSE  avgt   10     0.004 ±    0.009    B/op
StatBenchmark.aggregate:·gc.count                        AI_REUSE  avgt   10       ≈ 0             counts
```
