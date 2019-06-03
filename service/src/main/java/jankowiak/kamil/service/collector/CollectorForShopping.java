package jankowiak.kamil.service.collector;

import jankowiak.kamil.persistence.model.Order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class CollectorForShopping implements Collector<Order, BigDecimal[], BigDecimal> {

    private int counter[] = {0};

    @Override
    public Supplier<BigDecimal[]> supplier() {
        return () -> new BigDecimal[]{BigDecimal.ZERO};
    }

    @Override
    public BiConsumer<BigDecimal[], Order> accumulator() {
        return (acc, order) -> {
            acc[0] = acc[0].add(order.getProduct().getPrice().multiply(new BigDecimal(order.getQuantity())));
            counter[0] += order.getQuantity();
        };
    }

    @Override
    public BinaryOperator<BigDecimal[]> combiner() {
        return (res1, res2) ->  {
            res1[0] = res1[0].add(res2[1]);
            return res1;
        };
    }

    @Override
    public Function<BigDecimal[], BigDecimal> finisher() {
        return total -> counter[0] == 0 ? BigDecimal.ZERO : total[0].divide(BigDecimal.valueOf(counter[0]), 2, RoundingMode.CEILING);
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}
