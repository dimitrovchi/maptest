package org.dimitrovchi.maptest.stat;

import java.util.function.Supplier;

/**
 * @author Dmitry Ovchinnikov
 */
public enum StatAggregatorSelector {

    ADDER_NEW(StatEventAggregatorAdderNew::new),
    ADDER_REUSE(StatEventAggregatorAdderReuse::new),
    AI_NEW(StatEventAggregatorAiNew::new),
    AI_REUSE(StatEventAggregatorAiReuse::new);

    final Supplier<StatAggregator> statAggregatorSupplier;

    StatAggregatorSelector(Supplier<StatAggregator> statAggregatorSupplier) {
        this.statAggregatorSupplier = statAggregatorSupplier;
    }
}
